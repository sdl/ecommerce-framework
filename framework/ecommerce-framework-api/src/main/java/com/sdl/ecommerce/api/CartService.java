package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Cart;

/**
 * Cart Factory
 *
 * @author nic
 */
public interface CartService {

    /**
     * Create a new cart
     * @return cart instance
     * @throws ECommerceException
     */
    Cart createCart() throws ECommerceException;

    /**
     * Add product to cart
     * @param cartId
     * @param sessionId
     * @param productId
     * @param quantity
     * @return
     * @throws ECommerceException
     */
    Cart addProductToCart(String cartId, String sessionId, String productId, int quantity) throws ECommerceException;

    /**
     * Remove product from cart
     * @param cartId
     * @param sessionId
     * @param productId
     * @return
     * @throws ECommerceException
     */
    Cart removeProductFromCart(String cartId, String sessionId, String productId) throws ECommerceException;

}
