package com.sdl.ecommerce.api.edit.impl;

import com.sdl.ecommerce.api.edit.EditMenu;
import com.sdl.ecommerce.api.edit.MenuItem;

import java.util.List;

/**
 * EditMenuImpl
 *
 * @author nic
 */
public class EditMenuImpl implements EditMenu {

    private String title;

    private List<MenuItem> menuItems;

    public EditMenuImpl(String title, List<MenuItem> menuItems) {
        this.title = title;
        this.menuItems = menuItems;
    }

    public String getTitle() {
        return title;
    }
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
