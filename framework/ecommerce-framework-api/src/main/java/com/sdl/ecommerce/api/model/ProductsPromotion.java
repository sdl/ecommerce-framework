package com.sdl.ecommerce.api.model;

import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.List;

/**
 * Products Promotion
 *
 * @author nic
 */
public interface ProductsPromotion extends Promotion {

    List<Product> getProducts();
}
