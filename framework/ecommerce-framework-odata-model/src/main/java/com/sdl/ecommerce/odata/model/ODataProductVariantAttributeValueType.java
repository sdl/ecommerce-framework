package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ProductVariantAttributeValueType;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataProductVariantAttributeValueType
 *
 * @author nic
 */
@EdmComplex(name = "ProductVariantAttributeValueType", namespace = "SDL.ECommerce")
public class ODataProductVariantAttributeValueType implements ProductVariantAttributeValueType {

    @EdmProperty
    private String id;

    @EdmProperty
    private String value;

    @EdmProperty
    private boolean isSelected;

    public ODataProductVariantAttributeValueType() {}
    public ODataProductVariantAttributeValueType(ProductVariantAttributeValueType valueType) {
        this.id = valueType.getId();
        this.value = valueType.getValue();
        this.isSelected = valueType.isSelected();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }
}
