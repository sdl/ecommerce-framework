using SDL.Demandware.Ecl;
using SDL.ECommerce.Ecl;
using System.Collections.Generic;
using System.Xml.Linq;

namespace SDL.DemandWare.Ecl
{
    /// <summary>
    /// Demandware Product Catalog. 
    /// </summary>
    public class DemandWareProductCatalog : ProductCatalog
    {
        private ShopClient shopClient;

        public DemandWareProductCatalog(XElement configuration)
        {           
            this.shopClient = new ShopClient(configuration.Element(EclProvider.EcommerceEclNs + "ShopUrl").Value,
                                             configuration.Element(EclProvider.EcommerceEclNs + "ClientId").Value);
        }

        public DemandWareProductCatalog(string shopUrl, string clientId)
        {
            this.shopClient = new ShopClient(shopUrl, clientId);
        }

        public Category GetAllCategories()
        {
            return (Category) this.shopClient.GetAllCategories();
        }

        public Category GetCategory(string id)
        {
            return (Category) this.shopClient.GetCategory(id);
        }

        public IList<Product> GetProducts(string categoryId, int publicationId = 0)
        {
            List<DemandWareProduct> list = (List<DemandWareProduct>) this.shopClient.GetProducts(categoryId, 100);
            return list.ConvertAll(x => (Product)x);
        }

        public Product GetProduct(string id, int publicationId = 0)
        {
            return (Product) this.shopClient.GetProduct(id);
        }
    }
}
