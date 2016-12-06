using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;

using System;
using System.Collections.Generic;
using System.Web.Mvc;

namespace SDL.ECommerce.DXA.Controllers
{
    /// <summary>
    /// E-Commerce Product Page Controller
    /// </summary>
    public class ProductPageController : AbstractECommercePageController
    {
        /// <summary>
        /// Product Page controller action.
        /// Finds the product and resolve correct template page for displaying the product.
        /// </summary>
        /// <param name="productUrl"></param>
        /// <returns></returns>
        public ActionResult ProductPage(string productUrl)
        {
            var pathTokens = productUrl.Split( new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            string productSeoId;
            String productId;
            if (pathTokens.Length == 2)
            {
                productSeoId = pathTokens[0];
                productId = pathTokens[1];
            }
            else if (pathTokens.Length == 1)
            {
                productSeoId = null;
                productId = pathTokens[0];
            }
            else
            {
                Log.Warn("Invalid product URL: " + productUrl);
                return NotFound();
            }

            IProduct product = null;
            var queryParams = HttpContext.Request.QueryString;
            if ( queryParams.Count > 0 )
            {
                // Get variant attributes from the query string
                //
                var variantAttributes = new Dictionary<string, string>();
                foreach (var key in queryParams.Keys)
                {
                    string attributeId = key.ToString();
                    string attributeValue = queryParams[attributeId];
                    variantAttributes.Add(attributeId, attributeValue); 
                }
                product = ECommerceContext.Client.DetailService.GetDetail(productId, variantAttributes);
            }

            if (product == null)
            {
                product = ECommerceContext.Client.DetailService.GetDetail(productId);
            }
            if ( product == null )
            {
                Log.Info("Product not found: " + productId);
                return NotFound();
            }
            PageModel templatePage = this.ResolveTemplatePage(this.GetSearchPath(productSeoId, product));
            if ( templatePage == null )
            {
                Log.Error("Product template page could not be found for product URL: " + productUrl);
                return NotFound();
            }

            templatePage.Title = product.Name;
            ECommerceContext.Set(ECommerceContext.PRODUCT, product);
            ECommerceContext.Set(ECommerceContext.URL_PREFIX, ECommerceContext.LocalizePath("/c"));
            return View(templatePage);

        }

        /// <summary>
        /// Get search path to find an appropriate CMS template page for current product.
        /// </summary>
        /// <param name="productSeoId"></param>
        /// <param name="product"></param>
        /// <returns></returns>
        protected IList<string> GetSearchPath(string productSeoId, IProduct product)
        {
            var searchPath = new List<string>();
            var basePath = ECommerceContext.LocalizePath("/products/");

            // SEO id
            //
            if (productSeoId != null)
            {
                searchPath.Add(basePath + productSeoId);
            }

            // Product ID
            //
            searchPath.Add(basePath + product.Id);
            
            // Product Categories
            //
            if (product.Categories != null)
            {
                foreach (var category in product.Categories)
                {
                    searchPath.Add(basePath + category.Id);
                }
            }
            
            // Generic fallback product look&feel
            //
            searchPath.Add(basePath + "generic");

            return searchPath;
        }
    }
}