package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.odata.model.ODataCategory;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmNavigationProperty;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.List;

/**
 * OData Cached Category
 *
 * @author nic
 */
@EdmEntity(name="Category", key = "id", namespace = "SDL.ECommerce")
@EdmEntitySet(name="Categories")
public class ODataCachedCategory implements Category {

    @EdmProperty(name = "id", nullable = false)
    protected String id;

    @EdmProperty(name = "name", nullable = false)
    protected String name;

    // Property only used for query based on path
    //
    @EdmProperty(name = "path")
    private String path;

    @EdmProperty(name = "pathName")
    private String pathName = null;

    @EdmNavigationProperty(name = "parent")
    private ODataCategory parent = null;

    @EdmNavigationProperty(name = "categories")
    private List<ODataCategory> categories = null;

    @EdmProperty(name= "parentIds")
    private List<String> parentIds = null;

    private List<Category> cachedCategories = null;

    private Category cachedParent = null;

    private long expiryTime;

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
        return this.cachedParent;
    }

    void setParent(Category category) {
        this.cachedParent = category;
    }

    @Override
    public List<Category> getCategories() {
        return this.cachedCategories;
    }

    @Override
    public String getPathName() {
        return this.pathName;
    }

    void setCategories(List<Category> categories, long expiryTime) {
        this.cachedCategories = categories;
        this.expiryTime= expiryTime;
    }

    boolean needRefresh()
    {
        return this.cachedCategories == null || this.expiryTime < System.currentTimeMillis();
    }

    List<String> getParentIds() {
        return this.parentIds;
    }

}
