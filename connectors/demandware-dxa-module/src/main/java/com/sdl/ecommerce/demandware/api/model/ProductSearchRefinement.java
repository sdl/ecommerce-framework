package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * ProductSearchRefinement
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSearchRefinement {

    private String attribute_id;
    private String label;
    private List<ProductSearchRefinementValue> values;

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ProductSearchRefinementValue> getValues() {
        return values;
    }

    public void setValues(List<ProductSearchRefinementValue> values) {
        this.values = values;
    }
}
