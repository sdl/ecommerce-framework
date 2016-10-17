using Sdl.Web.Common;
using Sdl.Web.Common.Configuration;
using Sdl.Web.Common.Interfaces;
using Sdl.Web.Common.Logging;
using Sdl.Web.Common.Models;
using Sdl.Web.Mvc.Configuration;
using Sdl.Web.Mvc.Controllers;
using Sdl.Web.Mvc.Formats;
using SDL.ECommerce.Api;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SDL.ECommerce.DXA.Controllers
{
    public abstract class AbstractECommercePageController : BaseController
    {

        /// <summary>
        /// Resolve Template Page
        /// </summary>
        /// <param name="searchPath"></param>
        /// <returns></returns>
        protected PageModel ResolveTemplatePage(IList<string> searchPath)
        {
            PageModel templatePage = null;
            foreach (var templatePagePath in searchPath)
            {
                try
                {
                    Log.Info("Trying to find page template: " + templatePagePath);
                    templatePage = ContentProvider.GetPageModel(templatePagePath, WebRequestContext.Localization);
                }
                catch (DxaItemNotFoundException) {}
                if (templatePage != null)
                {
                    break;
                }
            }
            if (templatePage != null)
            {
                //enrichRequest(request, templatePage, localization);
            }
            return templatePage;
        }

        /// <summary>
        /// Extract all facet parameters from the request
        /// </summary>
        /// <returns></returns>
        protected IList<FacetParameter> GetFacetParametersFromRequest()
        {
            var facetParameters = new List<FacetParameter>();

            var queryParams = HttpContext.Request.QueryString;
            foreach ( var key in queryParams.Keys ) // TODO: Use AllKeys here
            {
                string paramName = key.ToString(); 
                if ( !paramName.Equals("q") && !paramName.Equals("startIndex") )
                {
                    // TODO:  Use a global facet map here instead to validate against
                    var paramValue = queryParams[paramName];
                    facetParameters.Add(new FacetParameter(paramName, paramValue));
                }
            }
               
            return facetParameters;
        }

        protected ActionResult View(PageModel templatePage)
        {
            SetupViewData(templatePage);
            ControllerContext.RouteData.Values["controller"] = "Page";
            return View(templatePage.MvcData.ViewName, templatePage);
        }

        protected int GetStartIndex()
        {
            int startIndex = 0;
            var startIndexStr = HttpContext.Request.QueryString["startIndex"];
            if ( startIndexStr != null )
            {
                startIndex = Int32.Parse(startIndexStr);
            }
            return startIndex;
        }

        /// <summary>
        /// Render a file not found page
        /// </summary>
        /// <returns>404 page or HttpException if there is none</returns>
        [FormatData]
        protected virtual ActionResult NotFound()
        {
            using (new Tracer())
            {
                // TODO: Have the possiblity to have a E-Commerce specific 404 page for categories and products
                //
                string notFoundPageUrl = WebRequestContext.Localization.Path + "/error-404";

                PageModel pageModel;
                try
                {
                    pageModel = ContentProvider.GetPageModel(notFoundPageUrl, WebRequestContext.Localization);
                }
                catch (DxaItemNotFoundException ex)
                {
                    Log.Error(ex);
                    throw new HttpException(404, ex.Message);
                }

                SetupViewData(pageModel);
                ViewModel model = EnrichModel(pageModel) ?? pageModel;
                Response.StatusCode = 404;
                return View(model.MvcData.ViewName, model);
            }
        }

    }
}