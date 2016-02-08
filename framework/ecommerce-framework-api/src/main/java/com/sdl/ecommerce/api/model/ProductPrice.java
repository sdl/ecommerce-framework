package com.sdl.ecommerce.api.model;

/**
 * ProductPrice
 *
 * @author nic
 */
public interface ProductPrice {

    float getPrice();
    String getCurrency();
    String getFormattedPrice();
}
