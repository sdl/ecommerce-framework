using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.DXA.Models
{
    [SemanticEntity(Vocab = CoreVocabulary, EntityName = "ECommerceCategory", Prefix = "e")]
    public class ECommerceCategoryReference : EntityModel
    {
        [SemanticProperty("e:categoryPath")]
        public string CategoryPath { get; set; }

        // TODO: Add ECL reference here

        [SemanticProperty("e:categoryId")]
        public string CategoryId { get; set; }
    }
}
