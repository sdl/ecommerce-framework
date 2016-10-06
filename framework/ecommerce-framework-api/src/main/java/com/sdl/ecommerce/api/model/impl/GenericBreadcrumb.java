package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.CategoryRef;
import com.sdl.ecommerce.api.model.Facet;

/**
 * Generic Breadcrumb
 *
 * @author nic
 */
public class GenericBreadcrumb implements Breadcrumb {

    private String title;
    private String url;
    private boolean isCategory;
    private CategoryRef categoryRef;
    private Facet facet;

    // OLD CONSTRUCTOR - TO BE REMOVED !!!
    public GenericBreadcrumb(String title, String url, boolean isCategory) {
        this.title = title;
        this.url = url;
        this.isCategory = isCategory;
    }

    public GenericBreadcrumb(String title, CategoryRef categoryRef) {
        this.title = title;
        this.categoryRef = categoryRef;
        this.isCategory = true;
    }

    public GenericBreadcrumb(String title, Facet facet) {
        this.title = title;
        this.facet = facet;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isCategory() {
        return this.isCategory;
    }

    @Override
    public CategoryRef getCategoryRef() {
        return this.categoryRef;
    }

    @Override
    public Facet getFacet() {
        return this.facet;
    }
}
