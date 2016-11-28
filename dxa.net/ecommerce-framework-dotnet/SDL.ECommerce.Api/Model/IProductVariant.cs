using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    /// Product Variant
    /// </summary>
    public interface IProductVariant
    {
        /// <summary>
        /// Variant Product ID
        /// </summary>
        string Id { get; }

        /// <summary>
        /// Variant Price
        /// </summary>
        IProductPrice Price { get; }

        /// <summary>
        /// Variant Attributes
        /// </summary>
        IList<IProductVariantAttribute> Attributes { get; }
    }
}
