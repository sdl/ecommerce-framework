using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;
using Sdl.Web.Delivery.Service;

namespace SDL.ECommerce.OData
{
    public class CartService : ICartService
    {
        private ODataV4Service service;

        internal CartService(ODataV4Service service)
        {
            this.service = service;
        }

        public ICart CreateCart()
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("CreateCart");
            func.AllowCaching = false;
            return Enumerable.FirstOrDefault<Cart>(this.service.Execute<Cart>(func));
        }

        public ICart AddProductToCart(string cartId, string productId, int quantity)
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("AddProductToCart");
            func.AllowCaching = false;
            func.WithParam("cartId", cartId);
            func.WithParam("productId", productId);
            func.WithParam("quantity", quantity);
            return Enumerable.FirstOrDefault<Cart>(this.service.Execute<Cart>(func));
        }

        public ICart RemoveProductFromCart(string cartId, string productId)
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("RemoveProductFromCart");
            func.AllowCaching = false;
            func.WithParam("cartId", cartId);
            func.WithParam("productId", productId);
            return Enumerable.FirstOrDefault<Cart>(this.service.Execute<Cart>(func));
        }
    }
}
