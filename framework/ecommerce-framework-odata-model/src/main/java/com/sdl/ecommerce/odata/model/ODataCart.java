package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OData Cart
 *
 * @author nic
 */
@EdmEntity(name="Cart", key = "id", namespace = "SDL.ECommerce")
@EdmEntitySet(name="Carts")
public class ODataCart implements Cart {

    @EdmProperty
    private String id;

    @EdmProperty
    private List<ODataCartItem> items;

    @EdmProperty
    private int count;

    @EdmProperty
    private ODataProductPrice totalPrice;

    public ODataCart() {}
    public ODataCart(Cart cart) {
        this.id = cart.getId();
        this.items = new ArrayList<>();
        if ( cart.getItems() != null ) {
            cart.getItems().forEach(cartItem -> this.items.add(new ODataCartItem(cartItem)));
        }
        this.count = cart.count();
        if ( cart.getTotalPrice() != null ) {
            this.totalPrice = new ODataProductPrice(cart.getTotalPrice());
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<CartItem> getItems() {
        return this.items.stream().collect(Collectors.toList());
    }

    @Override
    public int count() {
        return this.count;
    }


    @Override
    public ProductPrice getTotalPrice() throws ECommerceException {
        return this.totalPrice;
    }

    @Override
    public Map<URI, Object> getDataToExposeToClaimStore() throws ECommerceException {
        return null;
    }
}
