using Sdl.Web.Common.Logging;

using System.Web.Mvc;
using System;
using System.Collections.Generic;

using SDL.ECommerce.Api.Model;

using Sdl.Web.Common.Models;

using SDL.ECommerce.Api;

using SDL.ECommerce.DXA.Servants;

namespace SDL.ECommerce.DXA.Controllers
{
    /// <summary>
    /// E-Commerce Category Page Controller
    /// </summary>
    public class CategoryPageController : AbstractECommercePageController
    {
        private readonly IECommerceClient _eCommerceClient;

        private readonly IECommerceLinkResolver _linkResolver;

        private readonly IHttpContextServant _httpContextServant;

        public CategoryPageController()
            : this(ECommerceContext.Client, new DXALinkResolver(), new HttpContextServant())
        {
        }

        internal CategoryPageController(IECommerceClient eCommerceClient, IECommerceLinkResolver linkResolver, IHttpContextServant httpContextServant)
        {
            _eCommerceClient = eCommerceClient;
            _linkResolver = linkResolver;
            _httpContextServant = httpContextServant;
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
                templatePage = this.ResolveTemplatePage(this.GetSearchPath(categoryUrl, category));
                templatePage.Title = category.Name;
                SetupViewData(templatePage);

                var query = new Api.Model.Query { Category = category, Facets = facets, StartIndex = GetStartIndex() };
                this.GetQueryContributions(templatePage, query);
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

        /// <summary>
        /// Get search path to find an appropriate CMS template page for current category.
        /// </summary>
        /// <param name="url"></param>
        /// <param name="category"></param>
        /// <returns></returns>
        protected IList<string> GetSearchPath(string url, ICategory category)
        {
            var searchPath = new List<string>();

            var basePath = ECommerceContext.LocalizePath("/categories/");
            var categoryPath = basePath;
            var currentCategory = category;
            while ( currentCategory != null)
            {
                searchPath.Add(categoryPath + currentCategory.Id);
                currentCategory = currentCategory.Parent;
            }
            var urlTokens = url.Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);
            foreach ( var token in urlTokens )
            {
                categoryPath += token;
                searchPath.Insert(0, categoryPath);
                categoryPath += "-";
            }
            searchPath.Add(basePath + "generic");
            return searchPath;
        }

    }
}