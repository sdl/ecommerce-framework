using System;
using System.Collections.Generic;
using System.Linq;
using SDL.ECommerce.Api.Model;

namespace SDL.ECommerce.OData
{
    public partial class Product : IProduct
    {
        string IProduct.Id
        {
            get
            {
                return this.Id;
            }
        }

        string IProduct.Name
        {
            get
            {
                return this.Name;
            }
        }

        string IProduct.Description
        {
            get
            {
                return this.Description;
            }
        }

        IProductPrice IProduct.Price
        {
            get
            {
                return this.Price;
            }
        }

        string IProduct.PrimaryImageUrl
        {
            get
            {
                return this.PrimaryImageUrl;
            }
        }

        string IProduct.ThumbnailUrl
        {
            get
            {
                return this.ThumbnailUrl;
            }
        }

        IDictionary<string, object> IProduct.Attributes
        {
            get
            {
                IDictionary<string, object> attributes = new Dictionary<string, object>();
                foreach ( var attribute in this.Attributes )
                {
                    object value;
                    if ( attribute.SingleValue != null )
                    {
                        value = attribute.SingleValue;
                    }
                    else
                    {
                        value = attribute.MultiValue;
                    }
                    attributes.Add(attribute.Name, value);
                }
                return attributes;
            }
        }

        IList<ICategory> IProduct.Categories
        {
            get
            {
                return this.Categories.Cast<ICategory>().ToList();
            }
        }

        IList<IBreadcrumb> IProduct.Breadcrumbs
        {
            get
            {
                return this.Breadcrumbs.Cast<IBreadcrumb>().ToList();
            }
        }

        IList<IPromotion> IProduct.Promotions
        {
            get
            {
                return this.Promotions.Select(promo => promo.ToConcretePromotion()).ToList();
            }
        }

    }
}
