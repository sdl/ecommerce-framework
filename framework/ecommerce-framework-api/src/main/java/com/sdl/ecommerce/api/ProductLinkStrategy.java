package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;

/**
 * Product Link Strategy
 *
 * @author nic
 */
public interface ProductLinkStrategy {

    /**
     * Get link to category page.
     *
     * @param category
     * @param urlPrefix
     * @return link
     */
    String getCategoryLink(Category category, String urlPrefix);

    /**
     * Get link to product detail page.
     * @param product
     * @return link
     */
    String getProductDetailLink(Product product);
}
