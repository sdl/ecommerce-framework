using Sdl.Web.Common.Models;
using Sdl.Web.Mvc.Controllers;
using Sdl.Web.Common.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Mvc;
using SDL.ECommerce.DXA.Models;
using SDL.ECommerce.Api;
using Sdl.Web.Mvc.Configuration;
using SDL.ECommerce.Api.Model;

namespace SDL.ECommerce.DXA.Controller
{
    public class EComWidgetController : BaseController
    {
        /// <summary>
        /// Product Detail
        /// </summary>
        /// <param name="entity"></param>
        /// <param name="containerSize"></param>
        /// <returns></returns>
        public ActionResult ProductDetail(EntityModel entity, int containerSize = 0)
        {
            Log.Info("Product Detail controller action triggered.");
            SetupViewData(entity, containerSize);

            ProductDetailWidget widget = (ProductDetailWidget) entity;

            if (widget.ProductReference != null)
            {
                // TODO: Resolve ECL reference here if any OR use product ID from page controller

                // TODO: Get locale from localization here

                // Get product details from E-Commerce service
                //
                widget.Product = ECommerceContext.Client.DetailService.GetDetail(widget.ProductReference.ProductId);
                Log.Info("Retrieved product with name: " + widget.Product.Name);
                // TODO: Add error handling when product is not found
            }
            else
            {
                // Use product from page controller
                //
                widget.Product = (IProduct) ECommerceContext.Get(ECommerceContext.PRODUCT);
            }

            return View(entity.MvcData.ViewName, entity);
        }

        /// <summary>
        /// Product Lister
        /// </summary>
        /// <param name="entity"></param>
        /// <param name="containerSize"></param>
        /// <returns></returns>
        public ActionResult ProductLister(EntityModel entity, int containerSize = 0)
        {
            Log.Info("Product lister controller action triggered.");
            SetupViewData(entity, containerSize);

            ProductListerWidget widget = (ProductListerWidget) entity;
            IProductQueryResult queryResult = null;
            if ( widget.CategoryReference != null )
            {
                string categoryId;
                if ( widget.CategoryReference.CategoryId != null )
                {
                    categoryId = widget.CategoryReference.CategoryId;
                }
                else if ( widget.CategoryReference.CategoryPath != null )
                {
                    ICategory category = ECommerceContext.Client.CategoryService.GetCategoryByPath(widget.CategoryReference.CategoryPath);
                    if ( category != null )
                    {
                        categoryId = category.Id;
                    }
                    else
                    {
                        throw new Exception("Invalid category path: " + widget.CategoryReference.CategoryPath);
                    }
                }
                else
                {
                    // Use ECL reference
                    //
                    categoryId = null;
                }
                queryResult = ECommerceContext.Client.QueryService.Query(new Query { CategoryId = categoryId });
            }
            else
            {
                // Use category from page controller
                //
                queryResult = (IProductQueryResult) ECommerceContext.Get(ECommerceContext.QUERY_RESULT);
            }

            widget.Items = queryResult.Products.ToList();

            return View(entity.MvcData.ViewName, entity);
        }

        /// <summary>
        /// Facets
        /// </summary>
        /// <param name="entity"></param>
        /// <param name="containerSize"></param>
        /// <returns></returns>
        public ActionResult Facets(EntityModel entity, int containerSize = 0)
        {
            SetupViewData(entity, containerSize);
            FacetsWidget widget = (FacetsWidget) entity;

            // Get facets
            //
            IProductQueryResult queryResult = null;
            if ( widget.CategoryReference != null )
            {
                // TODO: Use category reference to get facets
            }
            else
            {
                // Use category from page controller
                //
                queryResult = (IProductQueryResult)ECommerceContext.Get(ECommerceContext.QUERY_RESULT);
            }
            widget.FacetGroups = queryResult.FacetGroups.ToList();

            return View(entity.MvcData.ViewName, entity);
        }

        /// <summary>
        /// Promotions
        /// </summary>
        /// <param name="entity"></param>
        /// <param name="containerSize"></param>
        /// <returns></returns>
        public ActionResult Promotions(EntityModel entity, int containerSize = 0)
        {
            SetupViewData(entity, containerSize);
            PromotionsWidget widget = (PromotionsWidget)entity;

            // Get promotions
            //
            IProductQueryResult queryResult = null;
            if (widget.CategoryReference != null)
            {
                // TODO: Use category reference to get facets
            }
            else
            {
                // Use category from page controller
                //
                queryResult = (IProductQueryResult)ECommerceContext.Get(ECommerceContext.QUERY_RESULT);
            }

            // TODO: Fix so product detail also return a result object

            if ( queryResult != null )
            {
                widget.Promotions = queryResult.Promotions.ToList();
            }
            else
            {
                widget.Promotions = Enumerable.Empty<IPromotion>().ToList();
            }
           

            return View(entity.MvcData.ViewName, entity);
        }

        /// <summary>
        /// Breadcrumb
        /// </summary>
        /// <param name="entity"></param>
        /// <param name="containerSize"></param>
        /// <returns></returns>
        public ActionResult Breadcrumb(EntityModel entity, int containerSize = 0)
        {
            SetupViewData(entity, containerSize);
            BreadcrumbWidget widget = (BreadcrumbWidget)entity;

            // Get breadcrumb
            //
            IProductQueryResult queryResult = null;
            if (widget.CategoryReference != null)
            {
                // TODO: Use category reference to get facets
            }
            else
            {
                // Use category from page controller
                //
                queryResult = (IProductQueryResult)ECommerceContext.Get(ECommerceContext.QUERY_RESULT);
            }
            
            if ( queryResult != null )
            {
                widget.Breadcrumbs = queryResult.Breadcrumbs.ToList();
                // TODO: Add check if result is a product detail result -> do not show total count then
                widget.TotalItems = queryResult.TotalCount;
            }
            else
            {
                widget.Breadcrumbs = Enumerable.Empty<IBreadcrumb>().ToList();
            }
           

            return View(entity.MvcData.ViewName, entity);
        }

    }
}
