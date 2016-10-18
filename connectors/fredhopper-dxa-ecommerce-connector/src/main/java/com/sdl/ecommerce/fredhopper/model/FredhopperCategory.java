package com.sdl.ecommerce.fredhopper.model;

import com.fredhopper.webservice.client.Filtersection;
import com.sdl.ecommerce.api.model.Category;

import java.util.List;

/**
 * Fredhopper Category
 *
 * @author nic
 */
public class FredhopperCategory implements Category {

    private String id;
    private String name;
    public Category parent;
    private List<Category> categories = null;
    private long categoriesExpiryTime = 0;

    public FredhopperCategory(Category parent, String id, String name) {
        this.parent = parent;
        this.id = id;
        this.name = name;
    }

    public FredhopperCategory(Category parent, Filtersection section) {
        this.parent = parent;
        this.id = section.getValue().getValue();
        this.name = section.getLink().getName();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPathName () {
        return getPathName(name);
    }

    static public String getPathName(String name) {
        return name.toLowerCase().replace(" ", "_").replace("&", "and");
    }

    @Override
    public Category getParent() {
        return parent;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories;
        /* TODO: What overhead will this have????
        if ( categories != null ) {
            return Collections.unmodifiableList(categories);
        }
        else {
            return null;
        }
        */
    }

    public boolean needsRefresh() {
        return this.categories == null || this.categoriesExpiryTime < System.currentTimeMillis();
    }

    public void setCategories(List<Category> categories, long expiryTime) {
        this.categories = categories;
        this.categoriesExpiryTime = expiryTime;
    }

}
