using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.DXA.Models
{
    [SemanticEntity(Vocab = CoreVocabulary, EntityName = "ECommerceProduct", Prefix = "e")]
    public class ECommerceProductReference : EntityModel
    {
        [SemanticProperty("e:productId")]
        public string ProductId { get; set; }

        // TODO: Declare the ECL item here as well
    }
}
