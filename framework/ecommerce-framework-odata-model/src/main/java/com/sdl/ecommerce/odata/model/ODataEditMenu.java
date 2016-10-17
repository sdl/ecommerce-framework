package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.edit.EditMenu;
import com.sdl.ecommerce.api.edit.MenuItem;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * OData Edit Menu
 *
 * @author nic
 */
@EdmEntity(name="EditMenu", key ="menuId", namespace = "SDL.ECommerce")
@EdmEntitySet(name="EditMenus")
public class ODataEditMenu implements EditMenu {

    @EdmProperty
    private String menuId;

    @EdmProperty
    private String title;

    @EdmProperty
    private List<ODataMenuItem> menuItems;

    public ODataEditMenu() {}
    public ODataEditMenu(EditMenu editMenu, Query query) {
        this.title = editMenu.getTitle();
        this.menuId = this.title.replace(" ", "-").replace(".", "").toLowerCase();
        if ( query.getCategory() != null ) {
            this.menuId += "-" + query.getCategory().getId();
        }
        if ( query.getSearchPhrase() != null ) {
            this.menuId += "-" + query.getSearchPhrase().replace(" ", "-").toLowerCase();
        }
        this.menuItems = new ArrayList<>();
        if ( editMenu.getMenuItems() != null ) {
            editMenu.getMenuItems().forEach(menuItem -> this.menuItems.add(new ODataMenuItem(menuItem)));
        }
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return null;
    }
}
