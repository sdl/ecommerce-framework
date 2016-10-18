package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Facet;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * OData Facet
 *
 * @author nic
 */
@EdmComplex(name="Facet", namespace = "SDL.ECommerce")
public class ODataFacet implements Facet {

    @EdmProperty
    private String id;

    @EdmProperty
    private String title;

    @EdmProperty
    private int count;

    @EdmProperty
    private boolean isSelected;

    @EdmProperty
    private boolean isCategory;

    @EdmProperty
    private String type;

    @EdmProperty
    private List<String> values;

    public ODataFacet() {}
    public ODataFacet(Facet facet) {
        this.id = facet.getId();
        this.title = facet.getTitle();
        this.count = facet.getCount();
        this.isSelected = facet.isSelected();
        this.isCategory = facet.isCategory();
        this.type = facet.getType().name();
        this.values = facet.getValues();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean isCategory() {
        return this.isCategory;
    }

    @Override
    public FacetType getType() {
        return FacetType.valueOf(this.type);
    }

    @Override
    public String getValue() {
        if ( this.values != null && !this.values.isEmpty() ) {
            return this.values.get(0);
        }
        return null;
    }

    @Override
    public List<String> getValues() {
        return this.values;
    }
}
