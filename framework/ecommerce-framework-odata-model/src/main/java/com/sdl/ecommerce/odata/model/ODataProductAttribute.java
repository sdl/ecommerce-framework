package com.sdl.ecommerce.odata.model;

import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * OData Product Attribute
 *
 * @author nic
 */
@EdmComplex(name="ProductAttribute", namespace = "SDL.ECommerce")
public class ODataProductAttribute {

    @EdmProperty
    private String name;

    @EdmProperty
    private String singleValue;

    @EdmProperty
    private List<String> multiValue = new ArrayList<>();

    public ODataProductAttribute() {}

    public ODataProductAttribute(String name, Object value) {
        this.name = name;
        if ( value instanceof List ) {
            this.multiValue.addAll((List<String>) value);
        }
        else {
            this.singleValue = value.toString();
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getMultiValue() {
        return multiValue;
    }

    public String getSingleValue() {
        return singleValue;
    }
}
