package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Variation Attribute
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VariationAttribute {
    private String id;
    private String name;
    private List<VariationAttributeValue> values;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<VariationAttributeValue> getValues() {
        return values;
    }
}
