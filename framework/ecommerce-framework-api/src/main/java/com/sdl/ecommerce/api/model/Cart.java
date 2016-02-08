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

    String getId();

    void addProduct(Product product) throws ECommerceException;

    void addProduct(String productId) throws ECommerceException;

    List<CartItem> getItems();

    int count();

    void clear() throws ECommerceException;

    void refresh() throws ECommerceException;

    float getTotalPrice() throws ECommerceException;

    Map<URI,Object> getDataToExposeToClaimStore() throws ECommerceException;
}
