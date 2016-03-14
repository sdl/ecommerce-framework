package com.sdl.ecommerce.api.model;

/**
 * Facet
 *
 * @author nic
 */
public interface Facet {

    /**
     * Get facet title. Should be localized to current language.
     * @return
     */
    String getTitle();

    /**
     * Get URL to activate this facet on current result set.
     * @return url
     */
    String getUrl();

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
}
