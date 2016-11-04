package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ProductVariantAttribute;

/**
 * GenericProductVariantAttribute
 *
 * @author nic
 */
public class GenericProductVariantAttribute implements ProductVariantAttribute {

    private String id;
    private String name;
    private String valueId;
    private String value;

    public GenericProductVariantAttribute(String id, String name, String valueId, String value) {
        this.id = id;
        this.name = name;
        this.valueId = valueId;
        this.value = value;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValueId() {
        return this.valueId;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
