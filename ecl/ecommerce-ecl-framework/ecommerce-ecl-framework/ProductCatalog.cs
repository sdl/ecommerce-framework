using System.Collections.Generic;

namespace SDL.ECommerce.Ecl
{
    /// <summary>
    /// Product catalog interface.
    /// </summary>
    public interface ProductCatalog
    {
        /// <summary>
        /// Get all available catagories in the E-Commerce system.
        /// TODO: Improve with pagination here + possibility to use publication ID
        /// </summary>
        /// <returns></returns>
        Category GetAllCategories();

        /// <summary>
        /// Get category with specific identity.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        Category GetCategory(string id);

        /// <summary>
        /// Get products belonging to specific catagory.
        /// Publication ID can be used to get a localized version of the products (specific language/country)
        /// </summary>
        /// <param name="categoryId"></param>
        /// <param name="publicationId"></param>
        /// <returns></returns>
        IList<Product> GetProducts(string categoryId, int publicationId = 0);

        /// <summary>
        /// Get product with specified product identity/SKU.
        /// Publication ID can be used to get a localized version of the product.
        /// </summary>
        /// <param name="id"></param>
        /// <param name="publicationId"></param>
        /// <returns></returns>
        Product GetProduct(string id, int publicationId = 0);

    }
}
