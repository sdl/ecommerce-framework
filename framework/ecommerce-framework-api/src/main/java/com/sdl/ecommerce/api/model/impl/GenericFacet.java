package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Facet;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Facet
 *
 * @author nic
 */
public class GenericFacet implements Facet {

    private String id;
    private String title;
    private int count;
    private boolean isSelected;
    private boolean isCategory;
    private FacetType type;
    private List<String> values = new ArrayList<>();

    public GenericFacet(String id, String title, String value, FacetType type) {
        this.id = id;
        this.title = title;
        this.values.add(value);
        this.type = type;
    }

    public GenericFacet(String id,
                        String title,
                        List<String> values,
                        FacetType type,
                        boolean isSelected,
                        boolean isCategory,
                        int count) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.values.addAll(values);
        this.isSelected = isSelected;
        this.isCategory = isCategory;
        this.count = count;
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
        return this.type;
    }

    @Override
    public String getValue() {
        if ( !this.values.isEmpty() ) {
            return this.values.get(0);
        }
        return null;
    }

    @Override
    public List<String> getValues() {
        return this.values;
    }
}
