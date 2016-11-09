using Microsoft.OData.Client;
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

        public IProduct GetDetail(string productId)
        {
            try
            {
                return ((SDLECommerce)this.service.Service).Products.ByKey(productId).GetValue();
            }
            catch (DataServiceQueryException)
            {
                return null;
            }
        }

        public IProduct GetDetail(string productId, Dictionary<string, string> variantAttributes)
        {
            ODataV4ClientFunction func = new ODataV4ClientFunction("ProductVariant");
            func.AllowCaching = false;
            func.WithParam("productId", productId);
            if ( variantAttributes != null )
            {
                var variantAttributesStr = new StringBuilder();
                bool firstParam = true;
                foreach ( var attributeId in variantAttributes.Keys )
                {
                    if ( firstParam )
                    {
                        firstParam = false;
                    }
                    else
                    {
                        variantAttributesStr.Append("&");
                    }
                    variantAttributesStr.Append(attributeId + "=" + variantAttributes[attributeId]);
                }
                func.WithParam("variantAttributes", variantAttributesStr.ToString());
            }

            try
            {
                return Enumerable.FirstOrDefault<Product>(this.service.Execute<Product>(func));
            }
            catch (InvalidResourceException)
            {
                return null;
            }
            catch (DataServiceQueryException)
            {
                return null;
            }
        }
    }
}
