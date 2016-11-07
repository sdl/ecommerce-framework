using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface IProductVariantAttribute
    {
        string Id { get; }

        string Name { get; }

        string ValueId { get; }

        string value { get; }
    }
}
