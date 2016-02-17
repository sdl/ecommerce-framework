package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Cart;

/**
 * Cart Factory
 *
 * @author nic
 */
public interface CartFactory {

    /**
     * Create a new cart
     * @return cart instance
     * @throws ECommerceException
     */
    Cart createCart() throws ECommerceException;
}
