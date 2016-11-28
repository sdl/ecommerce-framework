using SDL.ECommerce.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.OData
{
    public partial class ProductVariantAttributeType : IProductVariantAttributeType
    {
        IList<IProductVariantAttributeValueType> IProductVariantAttributeType.Values
        {
            get
            {
                return this.Values.Cast<IProductVariantAttributeValueType>().ToList();
            }
        }
    }
}
