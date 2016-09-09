package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * Location
 *
 * @author nic
 */
public interface Location {

    /**
     * Get category reference
     * @return reference
     */
    CategoryRef getCategoryRef();

    /**
     * Get facet parameters
     * @return list of facet parameters
     */
    // TODO: Should this be Facet interface instead
    List<FacetParameter> getFacets();

    /**
     * Get product reference
     * @return reference
     */
    ProductRef getProductRef();

    /**
     * Static URL managed from the E-Commerce system
     * @return
     */
    String getStaticUrl();
}
