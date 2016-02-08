package com.sdl.ecommerce.api.model;

/**
 * CartItem
 *
 * @author nic
 */
public interface CartItem {

    ProductPrice getPrice();

    Product getProduct();

    int getQuantity();
}
