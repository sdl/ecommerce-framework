package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Cart
 *
 * @author nic
 */
@EdmEntity(namespace = "SDL.ECommerce", key = "id", containerName = "ECommerce")
@EdmEntitySet
public class Cart implements com.sdl.ecommerce.api.model.Cart {

    // TODO: Can we somewhow register proxies here instead???

    @EdmProperty(name = "id", nullable = false)
    private String id = "CART-777";

    @Override
    public void addProduct(String productId) throws ECommerceException {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void addProduct(String productId, int quantity) throws ECommerceException {

    }

    @Override
    public List<CartItem> getItems() {
        return null;
    }

    @Override
    public void removeProduct(String productId) throws ECommerceException {

    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void clear() throws ECommerceException {

    }

    @Override
    public void refresh() throws ECommerceException {

    }

    @Override
    public ProductPrice getTotalPrice() throws ECommerceException {
        return null;
    }

    @Override
    public Map<URI, Object> getDataToExposeToClaimStore() throws ECommerceException {
        return null;
    }
}
