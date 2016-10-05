using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;
using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
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
            
            // TODO: Migrate to product result here!!
            var result = ECommerceContext.Client.DetailService.GetDetail(productId);
            if ( result == null )
            {
                Log.Info("Product not found: " + productId);
                return NotFound();
            }
            PageModel templatePage = this.ResolveTemplatePage(this.GetSearchPath(productSeoId, result));
            if ( templatePage == null )
            {
                Log.Error("Product template page could not be found for product URL: " + productUrl);
                return NotFound();
            }

            templatePage.Title = result.Name;
            ECommerceContext.Set(ECommerceContext.PRODUCT, result);
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
            if (productSeoId != null)
            {
                searchPath.Add(basePath + productSeoId);

            }
            searchPath.Add(basePath + product.Id);
            /*
            if (product.Categories != null)
            {
                for (Category category : categories)
                {
                    searchPath.add(basePath + category.getId());
                }
            }
            */
            searchPath.Add(basePath + "generic");
            return searchPath;
        }
    }
}