using System;
using System.Collections.Generic;

namespace SDL.ECommerce.Api.Model
{
    public interface IFacet
    {
        /// <summary>
        /// Facet ID which uniquely identify the facet in URLs etc.
        /// </summary>
        /// <returns></returns>
        string Id { get; }

        /// <summary>
        ///  Facet title. Should be localized to current language.
        /// </summary>
        /// <returns></returns>
        string Title { get; }

        /// <summary>
        /// Get number of products in current result set.
        /// </summary>
        /// <returns></returns>
        int Count { get; }

        /// <summary>
        ///  Indicate if the facet has been selected (for multi-select).
        /// </summary>
        /// <returns></returns>
        bool IsSelected { get; }

        /// <summary>
        /// Indicate if the facet is an catagory facet.
        /// </summary>
        /// <returns></returns>
        bool IsCategory { get; }

        /// <summary>
        /// Type of facet
        /// </summary>
        /// <returns></returns>
        FacetType Type { get; }

        /// <summary>
        /// Get facet value if the facet is a single value facet (determined by the facet type)
        /// </summary>
        /// <returns></returns>
        string Value { get; }

        /// <summary>
        /// Get facet values if the facet is a multi value facet.
        /// </summary>
        /// <returns></returns>
        IList<String> Values { get; }
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
