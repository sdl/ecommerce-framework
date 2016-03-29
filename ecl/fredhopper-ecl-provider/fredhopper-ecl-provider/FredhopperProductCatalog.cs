
using com.fredhopper.lang.query;
using com.fredhopper.lang.query.location;
using com.fredhopper.lang.query.location.criteria;
using SDL.ECommerce.Ecl;
using SDL.Fredhopper.Ecl.FredhopperWS;
using System.Collections.Generic;
using System.Xml.Linq;

namespace SDL.Fredhopper.Ecl
{
    /// <summary>
    /// Fredhopper Product Catalog
    /// </summary>
    public class FredhopperProductCatalog : ProductCatalog
    {
        private FASWebServiceClient fhClient;

        private Category rootCategory = null;

        public FredhopperProductCatalog(XElement configuration)
        {
            // TODO: Configure the client

            this.fhClient = new FASWebServiceClient();

        }

        public Category GetAllCategories()
        {
            // TODO: This gonna be quite expensive however...
            // Can we do it structural instead???
            // A->B 

            // Only go 3 levels deep here (or have it configurable)
            if ( rootCategory == null )
            {
                rootCategory = new FredhopperCategory(null, null, null);
                this.GetCategoryTree(rootCategory, 0, 2);
            }
           
            return rootCategory;
        }

        private void GetCategoryTree(Category category, int level, int maxLevels)
        {
            this.GetCategories(category);

            foreach ( var subCategory in category.Categories )
            {
                if (level < maxLevels)
                {
                    this.GetCategoryTree(subCategory, level + 1, maxLevels);
                }
            }

        }

        public Category GetCategory(string categoryId)
        {
            return this.GetCategory(categoryId, this.rootCategory);
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

        public IList<Product> GetProducts(string categoryId, int publicationId)
        {
            return null;
        }

        public Product GetProduct(string id, int publicationId)
        {
            return null;
        }

        private void GetCategories(Category parentCategory)
        {
            var location = new Location("//catalog01/en-GB");
            if (parentCategory != null && parentCategory.CategoryId != null )
            {
                location.addCriterion(new CategoryCriterion("categories", parentCategory.CategoryId));
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
                        var category = new FredhopperCategory(section.value.Value, section.link.name, parentCategory);
                        parentCategory.Categories.Add(category);
                    }
                }
            }
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
    }
}
