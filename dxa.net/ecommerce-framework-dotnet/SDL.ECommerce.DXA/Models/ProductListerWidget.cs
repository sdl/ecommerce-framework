﻿using Sdl.Web.Common.Models;
using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.DXA.Models
{
    [SemanticEntity(Vocab = CoreVocabulary, EntityName = "ProductListerWidget", Prefix = "e")]
    public class ProductListerWidget : EntityModel, IQueryContributor
    {
        [SemanticProperty("e:category")]
        public ECommerceCategoryReference CategoryReference { get; set; }

        [SemanticProperty("e:viewSize")]
        public int? ViewSize { get; set; }

        [SemanticProperty("e:viewType")]
        public string ViewType { get; set; }

        [SemanticProperty(IgnoreMapping = true)]
        public IList<IProduct> Items { get; set; }
    
        [SemanticProperty(IgnoreMapping = true)]
        public ListerNavigationData NavigationData { get; set; }

        public void ContributeToQuery(Api.Model.Query query)
        {
            if ( ViewSize != null )
            {
                query.ViewSize = ViewSize;
            }
        }
    }
}
