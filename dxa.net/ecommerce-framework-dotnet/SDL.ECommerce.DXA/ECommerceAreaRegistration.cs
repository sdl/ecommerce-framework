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
            RegisterViewModel("ProductDetailEclItem", typeof(ECommerceEclItem), "EComWidget");
            RegisterViewModel("ProductLister", typeof(ProductListerWidget), "EComWidget");
            RegisterViewModel("Facets", typeof(FacetsWidget), "EComWidget");
            RegisterViewModel("Promotions", typeof(PromotionsWidget), "EComWidget");
            RegisterViewModel("Breadcrumb", typeof(BreadcrumbWidget), "EComWidget");
            RegisterViewModel("DetailBreadcrumb", typeof(BreadcrumbWidget), "EComWidget");
            RegisterViewModel("SearchFeedback", typeof(SearchFeedbackWidget), "EComWidget");
            RegisterViewModel("SearchBox", typeof(SearchBox));
            RegisterViewModel("FacetsMegaNavigation", typeof(FacetsWidget), "EComWidget");
            RegisterViewModel("CartMinimized", typeof(CartWidget), "EComWidget");
            RegisterViewModel("CartDetail", typeof(CartWidget), "EComWidget");
        }

        public override void RegisterArea(AreaRegistrationContext context)
        {
            base.RegisterArea(context);

            // E-Commerce Page Controllers
            //
            MapRoute(context, "ECommerce_Category", "c/{*categoryUrl}", 
                new { controller = "CategoryPage", action = "CategoryPage" });

            MapRoute(context, "ECommerce_Page", "p/{*productUrl}",
                new { controller = "ProductPage", action = "ProductPage" });

            MapRoute(context, "ECommerce_Search", "search/_redirect",
               new { controller = "SearchPage", action = "Search" });

            MapRoute(context, "ECommerce_SearchPage", "search/{searchPhrase}/{*categoryUrl}",
                new { controller = "SearchPage", action = "SearchCategoryPage" });

            // Localization routes
            //
            MapRoute(context, "ECommerce_Category_Loc", "{localization}/c/{*categoryUrl}",
                new { controller = "CategoryPage", action = "CategoryPage" });

            MapRoute(context, "ECommerce_Page", "{localization}/p/{*productUrl}",
                new { controller = "ProductPage", action = "ProductPage" });

            MapRoute(context, "ECommerce_Search", "search/_redirect",
               new { controller = "SearchPage", action = "Search" });

            MapRoute(context, "ECommerce_SearchPage", "{localization}/search/{searchPhrase}/{*categoryUrl}",
                new { controller = "SearchPage", action = "SearchCategoryPage" });

            // Cart controller
            //
            MapRoute(context, "ECommerce_AddToCart", "ajax/cart/addProduct/{productId}",
                new { controller = "Cart", action = "AddProductToCart" });

            MapRoute(context, "ECommerce_RemoveFromCart", "ajax/cart/removeProduct/{productId}",
                new { controller = "Cart", action = "RemoveProductFromCart" });

            // Edit Proxy route (only available for staging sites)
            //
            MapRoute(context, "ECommerce_EditProxy", "edit-proxy/{*path}", new { controller = "EditProxy", action = "Http" });

        }

        /// <summary>
        /// Map route for a page controller. 
        /// As this is called after the global DXA initialization we have to shuffle around the route definition so it comes before the DXA page controller.
        /// </summary>
        /// <param name="context"></param>
        /// <param name="name"></param>
        /// <param name="url"></param>
        /// <param name="defaults"></param>
        protected void MapRoute(AreaRegistrationContext context, string name, string url, object defaults)
        {
            var route = context.MapRoute(name, url, defaults);
            context.Routes.Remove(route);
            context.Routes.Insert(context.Routes.Count - 2, route);
        }

    }
}
