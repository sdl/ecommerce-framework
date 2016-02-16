package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * FacetGroup
 *
 * @author nic
 */
public interface FacetGroup {

    String getId();

    String getTitle();

    String getType();   // TODO: Have a standardised enum type here for the facet type (is not used right now anyway)

    List<Facet> getFacets();

    boolean isCategory();
}
