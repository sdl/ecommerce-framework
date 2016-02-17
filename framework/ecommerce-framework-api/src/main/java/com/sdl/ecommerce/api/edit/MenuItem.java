package com.sdl.ecommerce.api.edit;

/**
 * In-Context Menu Item
 *
 * @author nic
 */
public class MenuItem {

    private String title;
    private String url;

    public MenuItem(String title, String url) {
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
