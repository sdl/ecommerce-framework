using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sdl.Web.Mvc.Configuration;
using SDL.ECommerce.DXA.Models;
using Sdl.Web.Common.Logging;
using System.Web.Mvc;
using System.Web.Routing;

namespace SDL.ECommerce.DXA
{
    public class ECommerceAreaRegistration : BaseAreaRegistration
    {
        // TODO: Use Razor generator here??
        // http://stackoverflow.com/questions/12911006/asp-net-mvc-4-areas-in-separate-projects-not-working-view-not-found/12912161#12912161

        public override string AreaName
        {
            get { return "ECommerce"; }
        }

        protected override void RegisterAllViewModels()
        {
            // Entity Views
            //
            RegisterViewModel("ProductDetail", typeof(ProductDetailWidget), "EComWidget");
            RegisterViewModel("ProductLister", typeof(ProductListerWidget), "EComWidget");
            RegisterViewModel("Facets", typeof(FacetsWidget), "EComWidget");
            RegisterViewModel("Promotions", typeof(PromotionsWidget), "EComWidget");
            RegisterViewModel("Breadcrumb", typeof(BreadcrumbWidget), "EComWidget");
            RegisterViewModel("DetailBreadcrumb", typeof(BreadcrumbWidget), "EComWidget");
        }

        public override void RegisterArea(AreaRegistrationContext context)
        {
            base.RegisterArea(context);

            // E-Commerce Page Controllers
            //
            // TODO: Add routes for localization here as well!!!

            MapRoute(context, "ECommerce_Category", "c/{*categoryUrl}", 
                new { controller = "CategoryPage", action = "CategoryPage" });

            MapRoute(context, "ECommerce_Page", "p/{*productUrl}",
                new { controller = "ProductPage", action = "ProductPage" });
        }

        protected void MapRoute(AreaRegistrationContext context, string name, string url, object defaults)
        {
            var route = context.MapRoute(name, url, defaults);
            context.Routes.Remove(route);
            context.Routes.Insert(context.Routes.Count - 2, route);
        }

    }
}
