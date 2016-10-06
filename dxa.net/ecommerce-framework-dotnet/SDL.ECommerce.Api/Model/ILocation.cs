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
      
        /// <summary>
        /// Category reference
        /// </summary>
        ICategoryRef CategoryRef { get; }

        /// <summary>
        /// Facets
        /// </summary>
        IList<FacetParameter> Facets { get; }
        
        /// <summary>
        /// Product reference
        /// </summary>
        IProductRef ProductRef { get; }

        /// <summary>
        /// Static URL managed from the E-Commerce system
        /// </summary>
        /// <returns></returns>
        string StaticUrl { get; }
    }
}
