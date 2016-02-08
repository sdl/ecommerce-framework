package com.sdl.ecommerce.hybris.api.model;

/**
 * FacetPair
 *
 * @author nic
 */
public class FacetPair {

    private String id;
    private String value;

    public FacetPair(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
