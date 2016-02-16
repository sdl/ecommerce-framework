package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;

/**
 * Generic Cart Item
 *
 * @author nic
 */
public class GenericCartItem implements CartItem {

    private Product product;
    private int quantity;
    private ProductPrice price;

    public GenericCartItem(Product product, int quantity, ProductPrice price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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
