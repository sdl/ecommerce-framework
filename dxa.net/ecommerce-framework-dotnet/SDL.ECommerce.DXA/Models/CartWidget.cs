using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.ECommerce.DXA.Models
{
    [SemanticEntity(Vocab = CoreVocabulary, EntityName = "CartWidget", Prefix = "e")]
    public class CartWidget : EntityModel
    {
        [SemanticProperty("e:_self")]
        public string CartPageLink { get; set; }

        [SemanticProperty("e:checkoutLink")]
        public Link CheckoutLink { get; set; }

        // TODO: Add this when Cart model is available
        // public ICart Cart { get; set; }
    }
}