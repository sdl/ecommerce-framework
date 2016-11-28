package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ProductVariantAttributeValueType;

/**
 * GenericProductVariantAttributeValueType
 *
 * @author nic
 */
public class GenericProductVariantAttributeValueType implements ProductVariantAttributeValueType {

    private String id;
    private String value;
    private boolean isSelected;

    public GenericProductVariantAttributeValueType(String id, String value, boolean isSelected) {
        this.id = id;
        this.value = value;
        this.isSelected = isSelected;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
}
