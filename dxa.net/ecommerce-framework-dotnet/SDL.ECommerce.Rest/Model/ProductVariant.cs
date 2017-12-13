﻿using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Rest.Model
{
    public class ProductVariant : IProductVariant
    {
        public string Id { get; set; }
        public ProductPrice ProductPrice { get; set; }
        public List<ProductVariantAttribute> Attributes { get; set; }

        IProductPrice IProductVariant.Price
        {
            get
            {
                return ProductPrice;
            }
        }

        IList<IProductVariantAttribute> IProductVariant.Attributes
        {
            get
            {
                return Attributes.Cast<IProductVariantAttribute>().ToList();
            }
        }
    }
}
