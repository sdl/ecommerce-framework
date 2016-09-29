using System;
using System.Collections.Generic;

namespace SDL.ECommerce.Api.Model
{
    public interface IFacet
    {
        /**
         * Get facet ID to uniquely identify the facet in URLs etc.
         * @return id
         */
        string Id();

        /**
         * Get facet title. Should be localized to current language.
         * @return
         */
        string Title();

        /**
         * Get number of products in current result set.
         * @return count
         */
        int Count();

        /**
         * Indicate if the facet has been selected (for multi-select).
         * @return selected
         */
        bool IsSelected();

        /**
         * Indicate if the facet is an catagory facet.
         * @return true if facet is a category
         */
        bool IsCategory();

        /**
         * Get facet type.
         * @return type
         */
        FacetType Type();

        /**
         * Get facet value if the facet is a single value facet (determined by the facet type)
         * @return value
         */
        string Value();

        /**
         * Get facet values if the facet is a multi value facet.
         * @return values
         */
        List<String> Values();
    }

    public enum FacetType
    {
        // Single value facets
        SINGLEVALUE,
        RANGE,
        LESS_THAN,
        // Multi values facets
        MULTISELECT,
        GREATER_THAN
    }

}
