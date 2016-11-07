using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface IProductVariant
    {
    
        string Id { get; }

        IProductPrice Price { get; }

        IList<IProductVariantAttribute> Attributes { get; }
    }
}
