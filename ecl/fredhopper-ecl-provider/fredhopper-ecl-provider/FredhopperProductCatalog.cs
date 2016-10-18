
using com.fredhopper.lang.query;
using com.fredhopper.lang.query.location;
using com.fredhopper.lang.query.location.criteria;
using SDL.ECommerce.Ecl;
using SDL.Fredhopper.Ecl.FredhopperWS;
using System;
using System.Collections.Generic;
using System.ServiceModel;
using System.Xml.Linq;

namespace SDL.Fredhopper.Ecl
{
    /// <summary>
    /// Fredhopper Product Catalog
    /// </summary>
    public class FredhopperProductCatalog : ProductCatalog
    {
        private FASWebServiceClient fhClient;
        private IDictionary<int, PublicationConfiguration> publicationConfigurations = new Dictionary<int, PublicationConfiguration>();

        private IDictionary<string, Category>  rootCategories = new Dictionary<string, Category>();
        private int maxItems;
        private int categoryMaxDepth;

        public FredhopperProductCatalog(XElement configuration)
        {          
            var binding = new BasicHttpBinding();
            binding.MaxReceivedMessageSize = 1000000; // Needs be configurable?
            this.maxItems = Int32.Parse(configuration.Element(EclProvider.EcommerceEclNs + "MaxItems").Value);
            this.categoryMaxDepth = Int32.Parse(configuration.Element(EclProvider.EcommerceEclNs + "CategoryMaxDepth").Value); 
            var usernameElement = configuration.Element(EclProvider.EcommerceEclNs + "UserName");
            var passwordElement = configuration.Element(EclProvider.EcommerceEclNs + "Password");
            if ( usernameElement != null )
            {
                binding.Security.Mode = BasicHttpSecurityMode.TransportCredentialOnly;
                binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.Basic;
            }
            var endpointAddress = new EndpointAddress(configuration.Element(EclProvider.EcommerceEclNs + "EndpointAddress").Value);
            this.fhClient = new FASWebServiceClient(binding, endpointAddress);
            if ( usernameElement != null )
            {
                this.fhClient.ClientCredentials.UserName.UserName = usernameElement.Value;
                this.fhClient.ClientCredentials.UserName.Password = passwordElement.Value;
            }           

            // TODO: How to handle creation of new publications through SiteWizard? This will require some reconfig for each publication...
            // Use a fallback for those cases or???

            XElement publicationConfigurations = configuration.Element(EclProvider.EcommerceEclNs + "PublicationConfigurations");
            foreach (var config in publicationConfigurations.Elements(EclProvider.EcommerceEclNs + "PublicationConfiguration") )
            {
                var publicationIds = config.Attribute("publicationIds").Value; // Format: 12,34,45,55
                var publicationConfig = new PublicationConfiguration();
                publicationConfig.Locale = config.Element(EclProvider.EcommerceEclNs + "Locale").Value;
                publicationConfig.Universe = config.Element(EclProvider.EcommerceEclNs + "Universe").Value;
                var fallback = config.Attribute("fallback");
                if ( fallback != null && fallback.Value.Equals("true") )
                {
                    publicationConfig.Fallback = true;
                }
                var modelMappings = config.Element(EclProvider.EcommerceEclNs + "ModelMappings").Value;
                var modelMappingsMap = new Dictionary<string, string>();
                var tokens = modelMappings.Split(new char[] { ' ', ';', '=' }, StringSplitOptions.RemoveEmptyEntries);
                int i = 0;
                while ( i < tokens.Length )
                {
                    var name = tokens[i];
                    var value = tokens[i + 1];
                    modelMappingsMap.Add(name, value);
                    i = i + 2;
                }
                publicationConfig.ModelMappings = modelMappingsMap;
                var ids = publicationIds.Split(new char[] { ' ', ',' }, StringSplitOptions.RemoveEmptyEntries);
                foreach ( var id in ids )
                {
                    this.publicationConfigurations.Add(Int32.Parse(id), publicationConfig);
                }
            }

        }

        public Category GetAllCategories(int publicationId)
        {
           return this.GetRootCategory(publicationId);
        }

        public Category GetCategory(string categoryId, int publicationId)
        {
            return this.GetCategory(categoryId, this.GetRootCategory(publicationId));
        }

        private Category GetCategory(string categoryId, Category category)
        {
            if ( category == null || category.Categories == null )
            {
                return null;
            }
            foreach ( var subCategory in category.Categories)
            {
                if ( subCategory.CategoryId.Equals(categoryId) )
                {
                    return subCategory;
                }
                else
                {
                    Category matchingCategory = this.GetCategory(categoryId, subCategory);
                    if ( matchingCategory != null )
                    {
                        return matchingCategory;
                    }
                }
            }
            return null;
        }

        public QueryResult GetProducts(string categoryId, int publicationId, int pageIndex)
        {        
            // TODO: What products to show? Should we only show leaf products?
            // TODO: Have different configurable strategies here??

            Query query = new Query(GetLocation(publicationId, categoryId));
            query.setListStartIndex(pageIndex);
            return QueryProducts(query, publicationId);
        }

        public QueryResult Search(string searchTerm, string categoryId, int publicationId, int pageIndex)
        {
            Location location = categoryId != null ? GetLocation(publicationId, categoryId) : GetLocation(publicationId);
            location.addCriterion(new SearchCriterion(searchTerm));
            Query query = new Query(location);
            query.setListStartIndex(pageIndex);
            return QueryProducts(query, publicationId);
        }

        public Product GetProduct(string id, int publicationId)
        {
            var location = GetLocation(publicationId);
            Query query = new Query(location);
            query.addSecondId(id);
            query.setView(ViewType.DETAIL);
            page fhPage = this.fhClient.getAll(query.toString());
            universe universe = this.GetUniverse(fhPage);
            var modelMappings = this.GetPublicationConfiguration(publicationId).ModelMappings;
            var itemsSection = universe.itemssection;
            if (universe.itemssection != null)
            {
                foreach (var item in universe.itemssection.items)
                {
                    return new FredhopperProduct(item, modelMappings);
                }
            }
            return null;
        }

        private void GetCategories(Category parentCategory, int publicationId)
        {
            Location location;
            if (parentCategory != null && parentCategory.CategoryId != null )
            {
                location = GetLocation(publicationId, parentCategory.CategoryId);
            }
            else
            {
                location = GetLocation(publicationId); 
            }
            Query query = new Query(location);
            page fhPage = this.fhClient.getAll(query.toString());
           
            universe universe = this.GetUniverse(fhPage);

            var facetmap = universe.facetmap[0];
            var filters = facetmap.filter;
            foreach (var filter in filters)
            {
                if (filter.basetype == attributeTypeFormat.cat)
                {
                    foreach (var section in filter.filtersection)
                    {
                        if (parentCategory == null || !CategoryAlreadyExistInStructure(parentCategory, section.value.Value) )
                        {
                            var category = new FredhopperCategory(section.value.Value, section.link.name, parentCategory);
                            if (parentCategory != null)
                            {
                                parentCategory.Categories.Add(category);
                            }
                        }                        
                    }
                }
            }
        }

        private Category GetRootCategory(int publicationId)
        {
            var publicationConfiguration = this.GetPublicationConfiguration(publicationId);
            String cacheKey = publicationConfiguration.Universe + ":" + publicationConfiguration.Locale;
            Category rootCategory;
            this.rootCategories.TryGetValue(cacheKey, out rootCategory);
            if (rootCategory == null)
            {
                rootCategory = new FredhopperCategory(null, null, null);
                this.rootCategories.Add(cacheKey, rootCategory);
                this.GetCategoryTree(rootCategory, 0, this.categoryMaxDepth - 1, publicationId);
            }
            return rootCategory;
        }

        private void GetCategoryTree(Category category, int level, int maxLevels, int publicationId)
        {
            this.GetCategories(category, publicationId);

            foreach (var subCategory in category.Categories)
            {
                if (level < maxLevels)
                {
                    this.GetCategoryTree(subCategory, level + 1, maxLevels, publicationId);
                }
            }

        }

        private bool CategoryAlreadyExistInStructure(Category parentCategory, string categoryId)
        {
            if (parentCategory.CategoryId != null && parentCategory.CategoryId.Equals(categoryId))
            {
                return true;
            }
            return GetCategory(categoryId, ((FredhopperCategory) parentCategory).RootCategory) != null;
        }

        private Location GetLocation(int publicationId)
        {
            var publicationConfig = this.GetPublicationConfiguration(publicationId);
            if ( publicationConfig == null )
            {
                // TODO: Throw an exception here
                return null;
            }
            return new Location("//" + publicationConfig.Universe + "/" + publicationConfig.Locale);
        }

        private Location GetLocation(int publicationId, string categoryId)
        {
            var location = GetLocation(publicationId);
            location.addCriterion(new CategoryCriterion("categories", categoryId));
            return location;
        }

        private universe GetUniverse(page fhPage)
        {
            foreach (var universe in fhPage.universes)
            {
                if (universe.type == universeType.selected)
                {
                    return universe;

                }
            }
            return null;
        }

        private QueryResult QueryProducts(Query query, int publicationId)
        {
            query.setThemesDisabled(true);
            query.setListViewSize(this.maxItems);
            page fhPage = this.fhClient.getAll(query.toString());
            universe universe = this.GetUniverse(fhPage);
            var modelMappings = this.GetPublicationConfiguration(publicationId).ModelMappings;

            var result = new QueryResult();
            result.Products = new List<Product>();
            var itemsSection = universe.itemssection;
            if (universe.itemssection != null)
            {
                result.Total = universe.itemssection.results.total_items;
                result.NumberOfPages = universe.itemssection.results.total_items/universe.itemssection.results.view_size;
                if ( universe.itemssection.results.total_items % universe.itemssection.results.view_size != 0)
                {
                    result.NumberOfPages++;
                }
                foreach (var item in universe.itemssection.items)
                {
                    result.Products.Add(new FredhopperProduct(item, modelMappings));
                }
            }
            return result;
        }

        private PublicationConfiguration GetPublicationConfiguration(int publicationId)
        {
            if ( this.publicationConfigurations.ContainsKey(publicationId) )
            {
                return this.publicationConfigurations[publicationId];
            }
            else
            {
                // Use fallback
                //
                foreach ( var publicationConfig in this.publicationConfigurations.Values )
                {
                    if ( publicationConfig.Fallback )
                    {
                        return publicationConfig;
                    }
                }
            }
            return null; // TODO: Throw an exception here?
        }
    }

    class PublicationConfiguration
    {
        // TODO: Make a generic abstraction for this
        public string Universe { get; set; }
        public string Locale { get; set; }
        public IDictionary<string,string> ModelMappings { get; set; } 
        public bool Fallback { get; set;  }      
    }
}
