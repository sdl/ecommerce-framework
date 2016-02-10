package com.sdl.ecommerce.dummy.model;

import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;

/**
 * DummyCartItem
 *
 * @author nic
 */
public class DummyCartItem implements CartItem {

    private Product product;
    private int quantity;

    public DummyCartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public ProductPrice getPrice() {
        return this.product.getPrice();
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
