package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Category;

/**
 * ProductCategoryService
 *
 * @author nic
 */
public interface ProductCategoryService {

    Category getCategoryById(String id) throws ECommerceException;

    Category getCategoryByPath(String path) throws ECommerceException;

}
