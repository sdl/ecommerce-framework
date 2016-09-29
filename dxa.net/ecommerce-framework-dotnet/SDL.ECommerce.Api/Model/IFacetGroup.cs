using System;
using System.Collections.Generic;

namespace SDL.ECommerce.Api.Model
{
    /**
     * Facet Group.
     * Represents a set of facets (e.g. brand, color, size etc).
     *
     * @author nic
     */
    public interface IFacetGroup
    {

        /**
         * Get unique identifier for this facet group
         * @return id
         */
        String Id();

        /**
         * Get title of the facet group. Should be localized to current language.
         * @return
         */
        String Title();

        /**
         * Get all belonging facets
         * @return facets
         */
        List<IFacet> Facets();

        /**
         * Indicate if current facet group represents a product category or not.
         * @return is category
         */
        bool IsCategory();
    }
}
