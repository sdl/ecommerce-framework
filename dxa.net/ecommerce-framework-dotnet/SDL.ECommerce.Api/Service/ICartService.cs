using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Service
{
    public interface ICartService
    {
        /// <summary>
        /// Create new cart
        /// </summary>
        /// <returns></returns>
        ICart CreateCart();

        /// <summary>
        /// Add product to cart
        /// </summary>
        /// <param name="cartId"></param>
        /// <param name="productId"></param>
        /// <param name="quantity"></param>
        /// <returns></returns>
        ICart AddProductToCart(string cartId, string productId, int quantity);

        /// <summary>
        /// Remove product from cart
        /// </summary>
        /// <param name="cartId"></param>
        /// <param name="productId"></param>
        /// <returns></returns>
        ICart RemoveProductFromCart(string cartId, string productId);
    }
}
