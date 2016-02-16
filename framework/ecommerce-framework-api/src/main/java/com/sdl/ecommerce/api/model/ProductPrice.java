package com.sdl.ecommerce.api.model;

/**
 * ProductPrice
 *
 * @author nic
 */
public interface ProductPrice {

    float getPrice();
    String getCurrency(); // TODO: Have some kind of ISO code here???
    String getFormattedPrice();
}
