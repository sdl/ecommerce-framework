using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Sdl.Web.Mvc.Configuration;
using SDL.ECommerce.DXA.Models;
using Sdl.Web.Common.Logging;
using System.Web.Mvc;

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
          
        }
        
    }
}
