package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Category;

/**
 * Product Category Service
 * Is responsible to maintain the category structure.
 *
 * @author nic
 */
public interface ProductCategoryService {

    Category getCategoryById(String id) throws ECommerceException;

    Category getCategoryByPath(String path) throws ECommerceException;

}
