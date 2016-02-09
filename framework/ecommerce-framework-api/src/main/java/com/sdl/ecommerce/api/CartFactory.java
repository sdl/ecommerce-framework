package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Cart;

/**
 * Cart Factory
 *
 * @author nic
 */
public interface CartFactory {

    Cart createCart() throws ECommerceException;
}
