using Sdl.Web.Delivery.Service;
using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    /// <summary>
    /// E-Commerce Product Detail Service
    /// </summary>
    public class ProductDetailService : IProductDetailService
    {
        private ODataV4Service service;

        /// <summary>
        /// Constructor (only available internally)
        /// </summary>
        /// <param name="service"></param>
        internal ProductDetailService(ODataV4Service service)
        {
            this.service = service;
        }

        // TODO: This should return a product detail result with promos etc
        public IProduct GetDetail(string productId)
        {
            return ((SDLECommerce)this.service.Service).Products.ByKey(productId).GetValue();
        }
    }
}
