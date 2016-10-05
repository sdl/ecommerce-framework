using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    /// <summary>
    /// Location interface
    /// </summary>
    public interface ILocation
    {
      
        ICategoryRef CategoryRef();

        // TODO: Should this be Facet interface instead
        List<FacetParameter> Facets();

        IProductRef ProductRef();

        /// <summary>
        /// Static URL managed from the E-Commerce system
        /// </summary>
        /// <returns></returns>
        string StaticUrl();
    }
}
