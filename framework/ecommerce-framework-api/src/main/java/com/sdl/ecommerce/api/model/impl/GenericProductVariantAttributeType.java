package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ProductVariantAttributeType;
import com.sdl.ecommerce.api.model.ProductVariantAttributeValueType;

import java.util.List;

/**
 * GenericProductVariantAttributeType
 *
 * @author nic
 */
public class GenericProductVariantAttributeType implements ProductVariantAttributeType {

    private String id;
    private String name;
    private List<ProductVariantAttributeValueType> values;

    public GenericProductVariantAttributeType(String id, String name, List<ProductVariantAttributeValueType> values) {
        this.id = id;
        this.name = name;
        this.values = values;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ProductVariantAttributeValueType> getValues() {
        return values;
    }
}
