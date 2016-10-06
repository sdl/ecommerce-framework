package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.*;

import java.util.List;

/**
 * E-Commerce Link Resolver
 *
 * @author nic
 */
public interface ECommerceLinkResolver {

    /**
     * Get link to category page.
     *
     * @param category
     * @return link
     */
    String getCategoryLink(Category category);

    /**
     * Get facet link.
     * @param facet
     * @return link
     */
    String getFacetLink(Facet facet);

    /**
     * Get an absolute facet link using a base category path (for flyouts etc).
     * @param facet
     * @param navigationBasePath
     * @return link
     */
    String getAbsoluteFacetLink(Facet facet, String navigationBasePath);

    /**
     * Get breadcrumb link
     * @param breadcrumb
     * @return link
     */
    String getBreadcrumbLink(Breadcrumb breadcrumb);

    /**
     * Get link to location, normally a category + a set of facets)
     * @param location
     * @return location
     */
    String getLocationLink(Location location);

    /**
     * Get link to product detail page.
     * @param product
     * @return link
     */
    String getProductDetailLink(Product product);
}
