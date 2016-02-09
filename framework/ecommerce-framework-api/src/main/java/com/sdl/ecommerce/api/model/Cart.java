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

    String getId();


    // TODO: Should this be refactored to add cart items instead????
    void addProduct(String productId) throws ECommerceException;

    void addProduct(String productId, int quantity) throws ECommerceException;

    List<CartItem> getItems();

    void removeProduct(String productId) throws ECommerceException;

    int count();

    void clear() throws ECommerceException;

    void refresh() throws ECommerceException;

    ProductPrice getTotalPrice() throws ECommerceException;

    Map<URI,Object> getDataToExposeToClaimStore() throws ECommerceException;
}
