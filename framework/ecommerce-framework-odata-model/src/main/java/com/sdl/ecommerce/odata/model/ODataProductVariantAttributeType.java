package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ProductVariantAttributeType;
import com.sdl.ecommerce.api.model.ProductVariantAttributeValueType;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OData ProductVariantAttributeType
 *
 * @author nic
 */
@EdmComplex(name = "ProductVariantAttributeType", namespace = "SDL.ECommerce")
public class ODataProductVariantAttributeType implements ProductVariantAttributeType {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private List<ODataProductVariantAttributeValueType> values;

    public ODataProductVariantAttributeType() {}
    public ODataProductVariantAttributeType(ProductVariantAttributeType type) {
        this.id = type.getId();
        this.name = type.getName();
        if ( type.getValues() != null ) {
            values = new ArrayList<>();
            type.getValues().forEach(value -> values.add(new ODataProductVariantAttributeValueType(value)));
        }
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
    public List<ProductVariantAttributeValueType> getValues() {
        return this.values.stream().collect(Collectors.toList());
    }
}
