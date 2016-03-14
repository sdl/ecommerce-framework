package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.location.Location;
import com.sdl.ecommerce.api.ProductCategoryService;

/**
 * Fredhopper Link Manager
 * Manage promotion and image links.
 *
 * @author nic
 */
public interface FredhopperLinkManager {

    /**
     * Convert a FH location string to a SEO friendly link
     * @param location
     * @param categoryService
     * @return link
     */
    String convertToSEOLink(Location location, ProductCategoryService categoryService);

    /**
     * Convert a FH location to a SEO friendly link
     * @param location
     * @param categoryService
     * @return link
     */
    String convertToSEOLink(String location, ProductCategoryService categoryService);

    /**
     * Process image URL. For example remote image URLs can be rewritten to use local ones instead (if configured).
     * @param imageUrl
     * @return processed image URL
     */
    String processImageUrl(String imageUrl);

}
