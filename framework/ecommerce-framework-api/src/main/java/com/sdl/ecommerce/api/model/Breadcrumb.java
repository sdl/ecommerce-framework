package com.sdl.ecommerce.api.model;

/**
 * Breadcrumb
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
     * URL to a specific category. If breadcrumb is representing a facet then the URL is used to remove the facet itself.
     * @return url
     * @deprecated
     */
    // TODO: Remove this one!!
    String getUrl();

    /**
     * If breadcrumb is a category. If the breadcrumb is not a category it represents a facet.
     * @return true if breadcrumb is a category
     */
    boolean isCategory();

    Category getCategory();

    Facet getFacet();

    // For facets its a remove link but for categories is a navigation link

}
