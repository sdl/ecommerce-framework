package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.Breadcrumb;

/**
 * HybrisBreadcrumb
 *
 * @author nic
 */
public class HybrisBreadcrumb implements Breadcrumb {

    private String title;
    private String url;
    private boolean isCategory;

    public HybrisBreadcrumb(String title, String url, boolean isCategory) {
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
