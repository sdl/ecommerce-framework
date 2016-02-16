package com.sdl.ecommerce.dummy.model;

import com.sdl.ecommerce.api.model.ProductPrice;

/**
 * DummyPrice
 *
 * @author nic
 */
public class DummyPrice implements ProductPrice {

    private float price;
    private String formattedPrice;
    private String currency;

    public DummyPrice(ProductPrice initPrice, int quantity) {
        this.formattedPrice = initPrice.getFormattedPrice();
        if ( quantity > 1 ) {
            this.price = initPrice.getPrice()*quantity;
            this.formattedPrice = this.formattedPrice.replace(Float.toString(initPrice.getPrice()), Float.toString(this.price));
        }
        else {
            this.price = initPrice.getPrice();
        }
        this.currency = initPrice.getCurrency();
    }

    public void add(float priceToAdd) {
        float oldPrice = this.price;
        this.price += priceToAdd;
        this.formattedPrice = this.formattedPrice.replace(Float.toString(oldPrice), Float.toString(this.price));
    }

    @Override
    public float getPrice() {
        return this.price;
    }

    @Override
    public String getFormattedPrice() {
        return this.formattedPrice;
    }

    @Override
    public String getCurrency() {
        return this.currency;
    }
}
