package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Item
 *
 * @author nic
 */
public abstract class Item {

    // TODO: Is base class really needed? Overkill??

    protected String id;
    protected List<FacetParameter> facets;
    protected Map<String,Object> attributes = new HashMap<>();
    protected FredhopperLinkManager linkManager;

    protected Item(String id, FredhopperLinkManager linkManager) {
        this.id = id;
        this.linkManager = linkManager;
    }

    public String getId() {
        return id;
    }

    public List<FacetParameter> getFacets() {
        return facets;
    }

    public void setFacets(List<FacetParameter> facets) {
        this.facets = facets;
    }

    public Map<String,Object> getAttributes() {
        return this.attributes;
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }
}
