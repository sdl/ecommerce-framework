package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.hybris.api.model.Price;

/**
 * HybrisCartItem
 *
 * @author nic
 */
public class HybrisCartItem implements CartItem {

    private Product product;
    private int quantity;
    private ProductPrice price;

    public HybrisCartItem(Product product, int quantity, Price price) {
        this.product = product;
        this.quantity = quantity;
        this.price = new HybrisPrice(price);
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
