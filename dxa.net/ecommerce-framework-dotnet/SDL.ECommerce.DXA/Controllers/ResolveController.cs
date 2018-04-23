using Sdl.Web.Common.Configuration;
using Sdl.Web.Mvc.Configuration;
using System;
using System.Web.Mvc;
using SDL.ECommerce.Api;
using SDL.ECommerce.DXA.Factories;

namespace SDL.ECommerce.DXA.Controllers
{
    public class ResolveController : Controller
    {
        private readonly IECommerceLinkResolver _linkResolver;

        public ResolveController()
        {
            _linkResolver = DependencyFactory.Current.Resolve<IECommerceLinkResolver>();
        }
        
        /// <summary>
        /// Resolve to product detail page or a category page and redirect to that URL
        /// </summary>
        /// <param name="productId">Product ID</param>
        /// <param name="categoryId">Category ID</param>
        /// <param name="defaultPath">Default Path</param>
        /// <returns>null - response is redirected if the URL can be resolved</returns>
        public virtual ActionResult Resolve(string productId = null, string categoryId = null, string defaultPath = null)
        {
            string url = null;

            // TODO: Handle product variants as well

            var baseUrl = Request.Url.Scheme + "://" + Request.Url.Host;
            if ( !Request.Url.IsDefaultPort )
            {
                baseUrl += ":" + Request.Url.Port;
            }
            baseUrl += defaultPath; 
      
            Localization localization = SiteConfiguration.LocalizationResolver.ResolveLocalization(new Uri(baseUrl));
            WebRequestContext.Localization = localization;

            if (productId != null)
            {
                var product = ECommerceContext.Client.DetailService.GetDetail(productId);
                if (product != null)
                {
                    url = _linkResolver.GetProductDetailLink(product);
                }
            }
            else if (categoryId != null)
            {
                var category = ECommerceContext.Client.CategoryService.GetCategoryById(categoryId);
                if (category != null)
                {
                    url = _linkResolver.GetCategoryLink(category);
                }
            }
            if (url == null)
            {
                url = String.IsNullOrEmpty(defaultPath) ? "/" : defaultPath;
            }
            return Redirect(url);

        }
    }
}