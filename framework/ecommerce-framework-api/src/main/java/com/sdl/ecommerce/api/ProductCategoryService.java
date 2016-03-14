package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Category;

/**
 * Product Category Service
 * Is responsible to maintain the category structure.
 *
 * @author nic
 */
public interface ProductCategoryService {

    /**
     * Get category by ID.
     * @param id
     * @return category
     * @throws ECommerceException
     */
    Category getCategoryById(String id) throws ECommerceException;

    /**
     * Get category by path, e.g. /category1/category2.
     * Is based on the category name itself to provide a SEO friendly URL to categories.
     * @param path
     * @return category
     * @throws ECommerceException
     */
    Category getCategoryByPath(String path) throws ECommerceException;

}
