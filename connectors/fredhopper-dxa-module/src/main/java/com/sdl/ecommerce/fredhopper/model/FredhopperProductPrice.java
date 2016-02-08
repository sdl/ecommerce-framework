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

    private String formattedPrice;

    static private Pattern PRICE_PATTERN = Pattern.compile("[\\D ]*([0-9]*\\.[0-9]+|[0-9]+)[\\D ]*");

    public FredhopperProductPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    @Override
    public String getCurrency() {
        return null;
    }

    @Override
    public float getPrice() {
        Matcher priceMatcher = PRICE_PATTERN.matcher(formattedPrice);
        if ( priceMatcher.matches() ) {
            return Float.parseFloat(priceMatcher.group(1));
        }
        return 0.0f;
    }

    @Override
    public String getFormattedPrice() {
        return this.formattedPrice;
    }
}
