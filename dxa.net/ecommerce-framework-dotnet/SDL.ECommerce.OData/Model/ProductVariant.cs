using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    public partial class ProductVariant : IProductVariant
    {
        IProductPrice IProductVariant.Price
        {
            get
            {
                return this.Price;
            }
        }

        IList<IProductVariantAttribute> IProductVariant.Attributes
        {
            get
            {
                return this.Attributes.Cast<IProductVariantAttribute>().ToList();
            }
        }

    }
}
