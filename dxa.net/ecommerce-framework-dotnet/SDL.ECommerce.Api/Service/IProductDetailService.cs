using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Service
{
    /// <summary>
    /// E-Commerce Product Detail Service
    /// </summary>
    public interface IProductDetailService
    {
        IProduct GetDetail(string productId);
    }
}
