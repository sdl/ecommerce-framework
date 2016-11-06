package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ProductVariantAttribute;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataProductVariantAttribute
 *
 * @author nic
 */
@EdmComplex(name = "ProductVariantAttribute", namespace = "SDL.ECommerce")
public class ODataProductVariantAttribute implements ProductVariantAttribute {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String valueId;

    @EdmProperty
    private String value;

    public ODataProductVariantAttribute() {}
    public ODataProductVariantAttribute(ProductVariantAttribute variantAttribute) {
        this.id = variantAttribute.getId();
        this.name = variantAttribute.getName();
        this.valueId = variantAttribute.getValueId();
        this.value = variantAttribute.getValue();
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
