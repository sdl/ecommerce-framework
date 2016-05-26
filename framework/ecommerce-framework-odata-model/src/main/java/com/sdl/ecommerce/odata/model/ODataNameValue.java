package com.sdl.ecommerce.odata.model;

import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataNameValue
 *
 * @author nic
 */
@EdmComplex(name="NameValue", namespace = "SDL.ECommerce")
public class ODataNameValue {

    @EdmProperty
    private String name;

    @EdmProperty
    private String value;

    public ODataNameValue() {}

    public ODataNameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
