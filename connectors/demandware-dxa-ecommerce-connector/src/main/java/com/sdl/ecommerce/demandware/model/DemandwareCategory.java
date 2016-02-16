package com.sdl.ecommerce.demandware.model;

import com.sdl.ecommerce.api.model.Category;

import java.util.List;

/**
 * DemandwareCategory
 *
 * @author nic
 */
public class DemandwareCategory implements Category {

    // TODO: This class is identical with the Fredhopper one!!! Make a generic one!!!!
    private Category parent;
    private String id;
    private String name;
    private List<Category> categories = null;
    private long categoriesExpiryTime = 0;

    public DemandwareCategory(Category parent, String id, String name) {
        this.parent = parent;
        this.id = id;
        this.name = name;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Category getParent() {
        return this.parent;
    }

    @Override
    public String getCategoryLink(String urlPrefix) {
        String link = "";
        Category currentCategory = this;
        while ( currentCategory != null ) {
            link = currentCategory.getPathName() + "/" + link;
            currentCategory = currentCategory.getParent();
        }
        link = urlPrefix + "/" + link;
        return link;
    }

    @Override
    public String getPathName() {
        if ( this.name != null ) {
            return this.name.toLowerCase().replace(" ", "_").replace("&", "and");
        }
        else {
            return "";
        }
    }

    public boolean needsRefresh() {
        return this.categories == null || this.categoriesExpiryTime < System.currentTimeMillis();
    }

    public void setCategories(List<Category> categories, long expiryTime) {
        this.categories = categories;
        this.categoriesExpiryTime = expiryTime;
    }

}
