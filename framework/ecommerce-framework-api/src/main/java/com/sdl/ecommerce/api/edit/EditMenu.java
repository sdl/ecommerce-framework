package com.sdl.ecommerce.api.edit;

import java.util.List;

/**
 * EditMenu
 *
 * @author nic
 */
public class EditMenu {

    private String title;

    private List<MenuItem> menuItems;

    public EditMenu(String title, List<MenuItem> menuItems) {
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
