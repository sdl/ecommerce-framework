package com.sdl.ecommerce.api.model;

import com.sdl.ecommerce.api.ECommerceException;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Cart
 *
 * @author nic
 */
public interface Cart {

    // TODO: Should these URIs be here????? Maybe only the CART uri itself. The rest of the claims is something that can be unique for each implementation
    //

    URI CART_URI = URI.create("taf:claim:ecommerce:cart:object");
    URI CART_ITEMS_URI = URI.create("taf:claim:ecommerce:cart:items");
    URI CART_TOTAL_PRICE_URI = URI.create("taf:claim:ecommerce:cart:totalPrice");

    /**
     * @return unique identity of the cart
     */
    String getId();

    // TODO: Should this be refactored to add cart items instead???? Where there can be different sub classes to cart item
    // such as product cart item, promo code etc
    //

    /**
     * Add product (quantity=1) to the cart
     * @param productId
     * @throws ECommerceException
     */
    void addProduct(String productId) throws ECommerceException;

    /**
     * Add product with specific quantity to the cart.
     *
     * @param productId
     * @param quantity
     * @throws ECommerceException
     */
    void addProduct(String productId, int quantity) throws ECommerceException;

    /**
     * @return list of cart items added to the cart
     */
    List<CartItem> getItems();

    /**
     * Remove product from cart. Matching cart item containing this product ID will be removed
     * @param productId
     * @throws ECommerceException
     */
    // TODO: Remove product or cart item?
    void removeProduct(String productId) throws ECommerceException;

    /**
     * @return total count of products in the cart.
     */
    int count();

    /**
     * Clear the cart.
     *
     * @throws ECommerceException
     */
    void clear() throws ECommerceException;

    /**
     * Refresh the cart and sync it with underlying cart representation in the E-Commerce system.
     * @throws ECommerceException
     */
    void refresh() throws ECommerceException;

    /**
     * @return total price of everything added to the cart
     * @throws ECommerceException
     */
    ProductPrice getTotalPrice() throws ECommerceException;

    // TODO: Add other costs (tax, voucher discounts etc( as a attribute list which can be displayed in the cart detail page

    /**
     * @return data to be exposed to ADF claim store.
     * @throws ECommerceException
     */
    Map<URI,Object> getDataToExposeToClaimStore() throws ECommerceException;
}
