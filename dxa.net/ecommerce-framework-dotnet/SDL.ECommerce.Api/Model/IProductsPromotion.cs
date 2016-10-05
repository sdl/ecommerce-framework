using System.Collections.Generic;

namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    /// Promotion with products
    /// </summary>
    public interface IProductsPromotion : IPromotion 
    {
        /// <summary>
        ///  Get list of products enumerated in this promotion
        /// </summary>
        /// <returns></returns>
        List<IProduct> Products { get; }
    }
}
