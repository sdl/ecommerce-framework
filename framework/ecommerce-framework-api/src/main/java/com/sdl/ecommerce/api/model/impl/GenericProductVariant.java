package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.api.model.ProductVariant;
import com.sdl.ecommerce.api.model.ProductVariantAttribute;

import java.util.List;

/**
 * Generic Product Variant
 *
 * @author nic
 */
public class GenericProductVariant implements ProductVariant {

    private String id;
    private ProductPrice price;
    private List<ProductVariantAttribute> attributes;

    public GenericProductVariant(String id, ProductPrice price, List<ProductVariantAttribute> attributes) {
        this.id = id;
        this.price = price;
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public ProductPrice getPrice() {
        return this.price;
    }

    @Override
    public List<ProductVariantAttribute> getAttributes() {
        return this.attributes;
    }

    public ProductVariantAttribute getAttribute(String id) {
        if ( this.attributes != null ) {
            for ( ProductVariantAttribute attribute : this.attributes ) {
                if ( attribute.getId().equals(id) ) {
                    return attribute;
                }
            }
        }
        return null;
    }
}
