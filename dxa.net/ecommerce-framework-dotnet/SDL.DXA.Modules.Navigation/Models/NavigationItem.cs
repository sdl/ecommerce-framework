using Sdl.Web.Common.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SDL.DXA.Modules.Navigation.Models
{
    /// <summary>
    /// Navigation Item
    /// </summary>
    [SemanticEntity(Vocab = CoreVocabulary, EntityName = "NavigationItem", Prefix = "e")]
    public class NavigationItem : EntityModel
    {
        [SemanticProperty("e:link")]
        public Link Link { get; set; }

        [SemanticProperty("e:text")]
        public RichText Text { get; set; }

        [SemanticProperty("e:image")]
        public Image Image { get; set; }
    }
}