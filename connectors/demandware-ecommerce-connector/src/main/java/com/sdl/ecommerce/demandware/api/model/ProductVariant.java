package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Product Variant
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVariant {

    private String product_id;
    private boolean orderable;
    private float price;
    private Map<String,String> variation_values;

    public String getProduct_id() {
        return product_id;
    }

    public boolean isOrderable() {
        return orderable;
    }

    public float getPrice() {
        return price;
    }

    public Map<String, String> getVariation_values() {
        return variation_values;
    }
}
