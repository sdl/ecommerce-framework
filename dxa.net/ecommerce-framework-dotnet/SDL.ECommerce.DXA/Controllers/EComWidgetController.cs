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

namespace SDL.ECommerce.DXA.Controller
{
    public class EComWidgetController : BaseController
    {
        public ActionResult ProductDetail(EntityModel entity, int containerSize = 0)
        {
            Log.Info("Product Detail controller action triggered.");
            SetupViewData(entity, containerSize);

            ProductDetailWidget widget = (ProductDetailWidget) entity;

            // TODO: Resolve ECL reference here if any OR use product ID from page controller

            // TODO: Get locale from localization here

            // Get product details from E-Commerce service
            //
            widget.Product = ECommerce.GetClient("en_GB").DetailService.GetDetail(widget.ProductReference.ProductId);
            Log.Info("Retrieved product with name: " + widget.Product.Name);
            // TODO: Add error handling when product is not found

            return View(entity.MvcData.ViewName, entity);
        }
    }
}
