package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.CategoryRef;
import com.sdl.ecommerce.api.model.Facet;

/**
 * FredhopperBreadcrumb
 *
 * @author nic
 */
public class FredhopperBreadcrumb implements Breadcrumb {

    private String title;
    private String url;
    private boolean isCategory = true;

    // TODO: Add type here aswell!!

    public FredhopperBreadcrumb(String title, String url, boolean isCategory) {
        this.title = title;
        this.url = url;
        this.isCategory = isCategory;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCategory() {
        return isCategory;
    }

    // ---- Not implemented for the Fredhopper breadcrumb

    @Override
    public CategoryRef getCategoryRef() {
        return null;
    }

    @Override
    public Facet getFacet() {
        return null;
    }
}
