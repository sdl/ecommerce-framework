﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SDL.ECommerce.Api.Model;
using Newtonsoft.Json.Linq;
using SDL.ECommerce.Api.Service;

namespace SDL.ECommerce.Rest.Model
{
    // TODO: Have a model builder pipeline here that can modify/extend the model
    // Maybe make the IProduct interface a bit facetted...? For example one specific interface for VariantProduct etc 

    public class Product : IProduct
    {
        private IDictionary<string, object> _attributes;
        private IList<ICategory> _categories;

        public string Description { get; set; }
        public string Id { get; set; }
        public string MasterId { get; set; }
        public string VariantId { get; set; }
        public string Name { get; set; }
        public string PrimaryImageUrl { get; set; }
        public string ThumbnailUrl { get; set; }
        public ProductPrice Price { get; set; }
        public List<string> CategoryIds { get; set; }
        public Dictionary<string, object> Attributes { get; set; }
        public List<Promotion> Promotions { get; set; }
        public List<Breadcrumb> Breadcrumbs { get; set; }
        public List<ProductVariantAttribute> VariantAttributes { get; set; }
        public List<ProductVariantAttributeType> VariantAttributeTypes { get; set; }
        public List<ProductVariant> Variants { get; set; }
        public string VariantLinkType { get; set; }

        internal IProductCategoryService ProductCategoryService { get; set; }
   
        IList<IBreadcrumb> IProduct.Breadcrumbs
        {
            get
            {
                return Breadcrumbs?.Cast<IBreadcrumb>().ToList();
            }
        }

        IList<ICategory> IProduct.Categories
        {
            get
            {
                if ( _categories == null )
                {
                    _categories = new List<ICategory>();
                    if ( CategoryIds != null)
                    {
                        foreach (var categoryId in CategoryIds)
                        {
                            _categories.Add(new LazyCategory(categoryId, ProductCategoryService));
                        }
                    }

                }
                return _categories;
            }
        }
  
        IDictionary<string, object> IProduct.Attributes
        {
            get
            {
                if (_attributes == null)
                {
                    _attributes = new Dictionary<string, object>();
                    foreach (var attribute in Attributes.ToList())
                    {
                        if (attribute.Value is JArray)
                        {
                            var array = (JArray)attribute.Value;
                            _attributes[attribute.Key] = array.Select(i => i.ToString()).ToList();
                        }

                    }
                }
                return _attributes;
            }
        }

        IProductPrice IProduct.Price
        {
            get
            {
                return Price;
            }
        }
      
        IList<IPromotion> IProduct.Promotions
        {
            get
            {
                return Promotions?.Select(p => p.ToConcretePromotion()).ToList();
            }
        }

        IList<IProductVariantAttribute> IProduct.VariantAttributes
        {
            get
            {
                return VariantAttributes.Cast<IProductVariantAttribute>().ToList();
            }
        }

        IList<IProductVariantAttributeType> IProduct.VariantAttributeTypes
        {
            get
            {
                return VariantAttributeTypes.Cast<IProductVariantAttributeType>().ToList();
            }
        }

        VariantLinkType IProduct.VariantLinkType
        {
            get
            {
                return (VariantLinkType) Enum.Parse(typeof(VariantLinkType), VariantLinkType);
            }
        }

        IList<IProductVariant> IProduct.Variants
        {
            get
            {
                return Variants.Cast<IProductVariant>().ToList();
            }
        }
    }

}
