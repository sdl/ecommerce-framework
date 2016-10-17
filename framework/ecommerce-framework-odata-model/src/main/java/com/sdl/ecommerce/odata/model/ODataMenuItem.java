package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.edit.MenuItem;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * OData Menu Item
 *
 * @author nic
 */
@EdmComplex(name="MenuItem", namespace = "SDL.ECommerce")
public class ODataMenuItem implements MenuItem {

    @EdmProperty
    private String title;

    @EdmProperty
    private String url;

    public ODataMenuItem() {}
    public ODataMenuItem(MenuItem menuItem) {
        this.title = menuItem.getTitle();
        this.url = menuItem.getUrl();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
