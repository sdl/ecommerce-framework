using SDL.ECommerce.Api.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api
{
    public interface IECommerceClient
    {
        IProductDetailService DetailService { get; }
    }
}
