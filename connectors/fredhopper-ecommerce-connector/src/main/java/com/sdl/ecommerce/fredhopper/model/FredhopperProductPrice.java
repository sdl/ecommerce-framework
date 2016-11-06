package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.ProductPrice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FredhopperProductPrice
 *
 * @author nic
 */
public class FredhopperProductPrice implements ProductPrice {

    private float price;
    private String formattedPrice;

    public FredhopperProductPrice(float price, String formattedPrice) {
        this.price = price;
        this.formattedPrice = formattedPrice;
    }

    @Override
    public String getCurrency() {
        return null;
    }

    @Override
    public float getPrice() {
        return this.price;
    }

    @Override
    public String getFormattedPrice() {
        return this.formattedPrice;
    }
}
