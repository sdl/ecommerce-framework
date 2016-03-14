package com.sdl.ecommerce.api.model;

/**
 * Product Price
 *
 * @author nic
 */
public interface ProductPrice {

    /**
     * Get price in numeric format
     * @return price
     */
    float getPrice();

    /**
     * Get currency
     * @return currency
     */
    String getCurrency(); // TODO: Have some kind of ISO code here???

    /**
     * Get formatted price to be used in presentation views. This can be localized based on current locale.
     * @return formatted price
     */
    String getFormattedPrice();
}
