using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Service
{
    /// <summary>
    /// E-Commerce Product Detail Service
    /// </summary>
    public interface IProductDetailService
    {
        /// <summary>
        /// Get all product details for specified product identity/SKU.
        /// </summary>
        /// <param name="productId"></param>
        /// <returns></returns>
        IProduct GetDetail(string productId);

        /// <summary>
        /// Get all product details for specified product variant.
        /// </summary>
        /// <param name="productId"></param>
        /// <param name="variantAttributes"></param>
        /// <returns></returns>
        IProduct GetDetail(string productId, Dictionary<string, string> variantAttributes);
    }
}
