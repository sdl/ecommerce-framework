package com.sdl.ecommerce.api;

/**
 * Product Detail Service
 *
 * @author nic
 */
public interface ProductDetailService {

    /**
     * Get all product details with specified identity/SKU
     * @param productId
     * @return result with product details
     * @throws ECommerceException
     */
    ProductDetailResult getDetail(String productId) throws ECommerceException;
}
