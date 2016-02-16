using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Ecl
{
    public interface ProductCatalog
    {
        Category GetAllCategories();

        Category GetCategory(string id);

        // TODO: REMOVE THIS ONE. IS NOT NEEDED
        IList<string> GetProductIds(string categoryId);

        IList<Product> GetProducts(string categoryId, int publicationId = 0);

        // TODO: Have it optional for the concrete implementation to select what method to implement? I.e. GetProductIds() or GetProducts()??

        Product GetProduct(string id, int publicationId = 0);

        // TODO: Pass publication ID as well to be able to get a localized version of the entry????
    }
}
