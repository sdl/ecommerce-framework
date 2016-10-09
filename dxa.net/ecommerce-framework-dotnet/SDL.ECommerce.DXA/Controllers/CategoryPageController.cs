using Sdl.Web.Common.Logging;
using Sdl.Web.Mvc.Controllers;
using System.Web.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Sdl.Web.Mvc.Configuration;
using SDL.ECommerce.Api.Model;
using Sdl.Web.Common.Configuration;
using Sdl.Web.Common.Models;

namespace SDL.ECommerce.DXA.Controllers
{
    /// <summary>
    /// E-Commerce Category Page Controller
    /// </summary>
    public class CategoryPageController : AbstractECommercePageController
    {
        public ActionResult CategoryPage(string categoryUrl)
        {
            Log.Info("Entering category page controller with URL: " + categoryUrl);

            // Get facets
            //
            var facets = GetFacetParametersFromRequest();

            PageModel templatePage = null;
            var category = ECommerceContext.Client.CategoryService.GetCategoryByPath(categoryUrl);
            if ( category != null )
            {
                templatePage = this.ResolveTemplatePage(this.GetSearchPath(categoryUrl, category));
                templatePage.Title = category.Name;
                SetupViewData(templatePage);

                var searchResult = ECommerceContext.Client.QueryService.Query(new Query { Category = category, Facets = facets, StartIndex = GetStartIndex() });
                if ( searchResult.RedirectLocation != null )
                {
                    return Redirect(ECommerceContext.LinkResolver.GetLocationLink(searchResult.RedirectLocation));
                }

                ECommerceContext.Set(ECommerceContext.QUERY_RESULT, searchResult);
                ECommerceContext.Set(ECommerceContext.URL_PREFIX, ECommerceContext.LocalizePath("/c"));
                ECommerceContext.Set(ECommerceContext.FACETS, facets);
                ECommerceContext.Set(ECommerceContext.CATEGORY, category);
            }
            else
            {
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