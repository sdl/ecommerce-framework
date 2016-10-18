package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataCartItem
 *
 * @author nic
 */
@EdmComplex(name="CartItem", namespace = "SDL.ECommerce")
public class ODataCartItem implements CartItem {

    @EdmProperty
    private ODataProductPrice price;

    @EdmProperty
    private ODataProductSummary product;

    @EdmProperty
    private int quantity;

    public ODataCartItem() {}
    public ODataCartItem(CartItem cartItem) {
        this.price = new ODataProductPrice(cartItem.getPrice());
        this.product = new ODataProductSummary(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
    }

    @Override
    public ProductPrice getPrice() {
        return this.price;
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }
}
