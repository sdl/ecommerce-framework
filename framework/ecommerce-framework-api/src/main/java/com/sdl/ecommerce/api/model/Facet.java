package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * Facet
 *
 * @author nic
 */
public interface Facet {

    // TODO: Have sub classes to this one for Ranges etc???
    // RangeFacet, ValueFacet, LessThan, GreaterThan -> Do enums instead
    // getType(), getValues()

    // TODO: Move this out a separate file
    enum FacetType {
        // Single value facets
        SINGLEVALUE,
        RANGE,
        LESS_THAN,
        // Multi values facets
        MULTISELECT,
        GREATER_THAN
    }

    /**
     * Get facet ID to uniquely identify the facet in URLs etc.
     * @return id
     */
    String getId();

    /**
     * Get facet title. Should be localized to current language.
     * @return
     */
    String getTitle();

    /**
     * Get number of products in current result set.
     * @return count
     */
    int getCount();

    /**
     * Indicate if the facet has been selected (for multi-select).
     * @return selected
     */
    boolean isSelected();

    /**
     * Indicate if the facet is an catagory facet.
     * @return true if facet is a category
     */
    boolean isCategory();

    /**
     * Get facet type.
     * @return type
     */
    FacetType getType();

    /**
     * Get facet value if the facet is a single value facet (determined by the facet type)
     * @return value
     */
    String getValue();

    /**
     * Get facet values if the facet is a multi value facet.
     * @return values
     */
    List<String> getValues();


}
