package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Location;

/**
 * Fredhopper Link Manager
 * Manage promotion and image links.
 *
 * @author nic
 */
public interface FredhopperLinkManager {

    /**
     * Resolves a E-Commerce Framework location from a Fredhopper location
     * @param fhLocation
     * @param categoryService
     * @return location
     */
    Location resolveLocation(String fhLocation, ProductCategoryService categoryService);

    /**
     * Process image URL. For example remote image URLs can be rewritten to use local ones instead (if configured).
     * @param imageUrl
     * @return processed image URL
     */
    String processImageUrl(String imageUrl);



}
