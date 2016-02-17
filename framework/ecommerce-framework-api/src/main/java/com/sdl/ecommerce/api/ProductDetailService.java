package com.sdl.ecommerce.api;

/**
 * Product  DetailService
 *
 * @author nic
 */
public interface ProductDetailService {

    ProductDetailResult getDetail(String productId) throws ECommerceException;
}
