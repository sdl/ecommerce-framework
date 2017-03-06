using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;

using System;
using System.Web.Mvc;

using SDL.ECommerce.Api;
using SDL.ECommerce.DXA.Factories;
using SDL.ECommerce.DXA.Servants;

namespace SDL.ECommerce.DXA.Controllers
{
    using System.Linq;

    /// <summary>
    /// E-Commerce Product Page Controller
    /// </summary>
    public class ProductPageController : AbstractECommercePageController
    {
        private readonly IECommerceClient _eCommerceClient;

        private readonly IPathServant _pathServant;

        public ProductPageController()
        {
            _eCommerceClient = DependencyFactory.Current.Resolve<IECommerceClient>();
            _pathServant = DependencyFactory.Current.Resolve<IPathServant>();
        }

        /// <summary>
        /// Product Page controller action.
        /// Finds the product and resolve correct template page for displaying the product.
        /// </summary>
        /// <param name="productUrl"></param>
        /// <returns></returns>
        public ActionResult ProductPage(string productUrl)
        {
            var pathTokens = productUrl.Split( new[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            string productSeoId;
            string productId;

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

            if (queryParams.Count > 0)
            {
                var variantAttributes = queryParams.AllKeys.ToDictionary(k => k, k => queryParams[k]);

                product = _eCommerceClient.DetailService.GetDetail(productId, variantAttributes);
            }

            if (product == null)
            {
                // TODO: Should we redirect to an URL without variants here? So it's clear for the user what's happening??
                product = _eCommerceClient.DetailService.GetDetail(productId);
            }

            if ( product == null )
            {
                Log.Info("Product not found: " + productId);

                return NotFound();
            }

            PageModel templatePage = PageModelServant.ResolveTemplatePage(_pathServant.GetSearchPath(productSeoId, product), ContentProvider);

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
    }
}