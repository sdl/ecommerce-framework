package com.sdl.ecommerce.api;

/**
 * ProductDetailService
 *
 * @author nic
 */
public interface ProductDetailService {

    ProductDetailResult getDetail(String productId) throws ECommerceException;
}
