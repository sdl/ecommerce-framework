package com.sdl.ecommerce.api.edit.impl;

import com.sdl.ecommerce.api.edit.MenuItem;

/**
 * MenuItemImpl
 *
 * @author nic
 */
public class MenuItemImpl implements MenuItem {

    private String title;
    private String url;

    public MenuItemImpl(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
