namespace SDL.ECommerce.DXA.Controllers
{
    using Sdl.Web.Common.Logging;
    using Sdl.Web.Mvc.Configuration;

    using System.Web.Mvc;
    using System;

    using Sdl.Web.Common.Models;

    using SDL.ECommerce.Api;
    using SDL.ECommerce.Api.Model;
    using SDL.ECommerce.DXA.Servants;
    using SDL.ECommerce.DXA.Factories;

    /// <summary>
    /// E-Commerce Category Page Controller
    /// </summary>
    public class CategoryPageController : AbstractECommercePageController
    {
        private readonly IECommerceClient _eCommerceClient;

        private readonly IECommerceLinkResolver _linkResolver;

        private readonly IHttpContextServant _httpContextServant;

        private readonly IPageModelServant _pageModelServant;

        private readonly IPathServant _pathServant;

        public CategoryPageController()
        {
            _eCommerceClient = DependencyFactory.Current.Resolve<IECommerceClient>();
            _linkResolver = DependencyFactory.Current.Resolve<IECommerceLinkResolver>();
            _httpContextServant = DependencyFactory.Current.Resolve<IHttpContextServant>();
            _pageModelServant = DependencyFactory.Current.Resolve<IPageModelServant>();
            _pathServant = DependencyFactory.Current.Resolve<IPathServant>();
        }

        public ActionResult CategoryPage(string categoryUrl)
        {
            Log.Info("Entering category page controller with URL: " + categoryUrl);
            
            if ( String.IsNullOrEmpty(categoryUrl) )
            {
                categoryUrl = "/";
            }

            var facets = _httpContextServant.GetFacetParametersFromRequest(HttpContext);

            PageModel templatePage = null;
            var category = _eCommerceClient.CategoryService.GetCategoryByPath(categoryUrl);
            if ( category != null )
            {
                templatePage = _pageModelServant.ResolveTemplatePage(_pathServant.GetSearchPath(categoryUrl, category, WebRequestContext.Localization), ContentProvider, WebRequestContext.Localization);
                _pageModelServant.SetTemplatePage(templatePage);
                templatePage.Title = category.Name;
                SetupViewData(templatePage);

                var query = new Api.Model.Query { Category = category, Facets = facets, StartIndex = _httpContextServant.GetStartIndex(HttpContext) };
                _pageModelServant.GetQueryContributions(templatePage, query);
                var searchResult = _eCommerceClient.QueryService.Query(query);
                if ( searchResult.RedirectLocation != null )
                {
                   return Redirect(_linkResolver.GetLocationLink(searchResult.RedirectLocation));
                }

                ECommerceContext.Set(ECommerceContext.CURRENT_QUERY, query);
                ECommerceContext.Set(ECommerceContext.QUERY_RESULT, searchResult);
                ECommerceContext.Set(ECommerceContext.URL_PREFIX, ECommerceContext.LocalizePath("/c"));
                ECommerceContext.Set(ECommerceContext.FACETS, facets);
                ECommerceContext.Set(ECommerceContext.CATEGORY, category);
            }
            else
            {
                Log.Warn("Category page with URL: /" + categoryUrl + " does not exists.");
                return NotFound();
            }

            return View(templatePage);
        }
    }
}