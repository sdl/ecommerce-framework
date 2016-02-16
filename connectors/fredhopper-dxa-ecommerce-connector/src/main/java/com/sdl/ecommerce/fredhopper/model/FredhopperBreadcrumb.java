package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.Breadcrumb;

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

    public String getUrl() {
        return url;
    }

    public boolean isCategory() {
        return isCategory;
    }
}
