package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Facet;

/**
 * Generic Facet
 *
 * @author nic
 */
public class GenericFacet implements Facet {

    private String title;
    private String url;
    private int count;
    private boolean isSelected;

    public GenericFacet(String title, String url, int count, boolean isSelected) {
        this.title = title;
        this.url = url;
        this.count = count;
        this.isSelected = isSelected;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }
}
