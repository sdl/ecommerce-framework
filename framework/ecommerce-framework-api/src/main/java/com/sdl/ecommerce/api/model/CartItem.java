package com.sdl.ecommerce.api.model;

/**
 * Cart Item
 *
 * @author nic
 */
public interface CartItem {

    // TODO: Refactor this into a abstract interface where a number of cart item types can be added, such as ProductItem, PromoItem

    /**
     * Get the accumulated price for the cart item.
     * @return price
     */
    ProductPrice getPrice();

    /**
     * Get product.
     * @return product
     */
    Product getProduct();

    /**
     * Get quantity of the current product
     * @return quantity
     */
    int getQuantity();
}
