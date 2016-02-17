package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.List;

/**
 * Product Detail Result.
 *
 * Result when retrieving product details.
 *
 * @author nic
 */
public interface ProductDetailResult extends ECommerceResult {

    /**
     * Get product detail.
     * @return full detail of the product requested
     */
    Product getProductDetail();
}
