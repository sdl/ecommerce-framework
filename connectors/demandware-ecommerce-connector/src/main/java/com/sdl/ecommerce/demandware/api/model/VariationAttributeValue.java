package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * VariationAttributeValue
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VariationAttributeValue {

    private String name;
    private String value;
    private boolean orderable;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isOrderable() {
        return orderable;
    }
}
