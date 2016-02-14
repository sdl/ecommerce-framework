package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Breadcrumb;

/**
 * Generic Breadcrumb
 *
 * @author nic
 */
public class GenericBreadcrumb implements Breadcrumb {

    private String title;
    private String url;
    private boolean isCategory;

    public GenericBreadcrumb(String title, String url, boolean isCategory) {
        this.title = title;
        this.url = url;
        this.isCategory = isCategory;
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
    public boolean isCategory() {
        return this.isCategory;
    }
}
