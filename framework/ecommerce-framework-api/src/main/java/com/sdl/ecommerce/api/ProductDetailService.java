package com.sdl.ecommerce.api;

import java.util.Map;

/**
 * Product Detail Service
 *
 * @author nic
 */
public interface ProductDetailService {

    /**
     * Get all product details for specified identity/SKU
     * @param productId
     * @return result with product details
     * @throws ECommerceException
     */
    ProductDetailResult getDetail(String productId) throws ECommerceException;

    /**
     * Get all product details for specified product variant.
     * @param productId
     * @param variantAttributes
     * @return result with product details
     * @throws ECommerceException
     */
    ProductDetailResult getDetail(String productId, Map<String,String> variantAttributes) throws ECommerceException;
}
