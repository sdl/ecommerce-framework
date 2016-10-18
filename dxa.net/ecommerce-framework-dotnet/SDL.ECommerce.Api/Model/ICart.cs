using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    public interface ICart
    {
        string Id { get; }
        int Count { get; }
        IProductPrice TotalPrice { get; }
        IList<ICartItem> Items { get; }
    }
}
