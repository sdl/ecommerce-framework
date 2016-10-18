using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api
{
    /// <summary>
    /// Product Reference interface
    /// </summary>
    public interface IProductRef
    {
   
        string Id { get; }

        string Name { get; }
       
    }
}
