package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.List;

/**
 * Product Detail Result
 *
 * @author nic
 */
public interface ProductDetailResult extends ECommerceResult {

    Product getProductDetail();
}
