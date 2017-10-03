using SDL.ECommerce.Api.Model;

using System.Collections.Generic;
using System.Linq;

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
