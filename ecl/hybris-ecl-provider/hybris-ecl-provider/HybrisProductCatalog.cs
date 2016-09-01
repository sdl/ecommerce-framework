using System;
using SDL.Ecommerce.Hybris.API;
using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using System.Xml.Linq;

namespace SDL.Hybris.Ecl
{
    /// <summary>
    /// Hybris Product Catalog
    /// </summary>
    public class HybrisProductCatalog : ProductCatalog
    {
        private HybrisClient hybrisClient;
        private string mediaUrl;

        public HybrisProductCatalog(XElement configuration)
        {           
            this.hybrisClient = new HybrisClient(configuration.Element(EclProvider.EcommerceEclNs + "ShopUrl").Value,
                                                 configuration.Element(EclProvider.EcommerceEclNs + "Username").Value,
                                                 configuration.Element(EclProvider.EcommerceEclNs + "Password").Value,
                                                 configuration.Element(EclProvider.EcommerceEclNs + "ActiveCatalogVersion").Value);
            this.mediaUrl = configuration.Element(EclProvider.EcommerceEclNs + "MediaUrl").Value;
        }

        public HybrisProductCatalog(string shopUrl, string username, string password, string activeCatalogVersion, string mediaUrl)
        {
            this.hybrisClient = new HybrisClient(shopUrl, username, password, activeCatalogVersion);
            this.mediaUrl = mediaUrl;
        }

        public Category GetAllCategories(int publicationId)
        {
            var categories = this.hybrisClient.GetAllCategories();
            return new HybrisRootCategory(categories);
        }

        public Category GetCategory(string categoryId, int publicationId)
        {
            var hybrisCategory = this.hybrisClient.GetCategory(categoryId);
            return new HybrisCategory(hybrisCategory);
        }

        public QueryResult GetProducts(string categoryId, int publicationId, int pageIndex)
        {
            //var hybrisCategory = this.hybrisClient.GetCategory(categoryId);
            var facets = new List<SDL.ECommerce.Hybris.API.Model.FacetPair>();
            facets.Add(new SDL.ECommerce.Hybris.API.Model.FacetPair("category", categoryId));
            var searchResult = this.hybrisClient.Search(null, 100, 0, null, facets);
            var result = new QueryResult();
            result.NumberOfPages = 1; // TODO: Add support for pagination
            result.Products = new List<Product>();
            foreach (var product in searchResult.products)
            {
                result.Products.Add(new HybrisProduct(product, this.mediaUrl));
            }
            return result;
        }

        public QueryResult Search(string searchTerm, string categoryId, int publicationId = 0, int pageIndex = 0)
        {
            throw new NotSupportedException();
        }

        public Product GetProduct(string id, int publicationId)
        {
            var hybrisProduct = this.hybrisClient.GetProduct(id);
            return new HybrisProduct(hybrisProduct, this.mediaUrl);
        }
    }
}
