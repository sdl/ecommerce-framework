package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * OData Product Price
 *
 * @author nic
 */
@EdmComplex(name="ProductPrice", namespace = "SDL.ECommerce", open = true)
public class ODataProductPrice implements ProductPrice {

    @EdmProperty
    private float price;

    @EdmProperty
    private String formattedPrice;

    @EdmProperty
    private String currency;

    public ODataProductPrice() {}

    public ODataProductPrice(ProductPrice productPrice) {
        this.price = productPrice.getPrice();
        this.formattedPrice = productPrice.getFormattedPrice(); // TODO: Should this be only presentation related???
        this.currency = productPrice.getCurrency();
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
