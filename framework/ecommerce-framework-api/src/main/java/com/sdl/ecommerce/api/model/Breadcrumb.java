package com.sdl.ecommerce.api.model;

/**
 * Breadcrumb
 * For facets its a remove link but for categories is a navigation link
 *
 * @author nic
 */
public interface Breadcrumb {

    /**
     * Breadcrumb title to be used for presentation. Should be localized for current language.
     * @return title
     */
    String getTitle();

    /**
     * If breadcrumb is a category. If the breadcrumb is not a category it represents a facet.
     * @return true if breadcrumb is a category
     */
    boolean isCategory();

    /**
     * Get category reference
     * @return reference
     */
    CategoryRef getCategoryRef();

    /**
     * Get facet
     * @return facet
     */
    Facet getFacet();

}
