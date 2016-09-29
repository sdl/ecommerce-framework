using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api.Model
{
    /**
     * Location
     *
     * @author nic
     */
    public interface ILocation
    {

        /**
         * Get category reference
         * @return reference
         */
        CategoryRef CategoryRef();

        /**
         * Get facet parameters
         * @return list of facet parameters
         */
        // TODO: Should this be Facet interface instead
        List<FacetParameter> Facets();

        /**
         * Get product reference
         * @return reference
         */
        ProductRef ProductRef();

        /**
         * Static URL managed from the E-Commerce system
         * @return
         */
        string StaticUrl();
    }
}
