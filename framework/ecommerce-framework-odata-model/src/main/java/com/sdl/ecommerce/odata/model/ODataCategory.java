package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmNavigationProperty;
import com.sdl.odata.api.edm.annotations.EdmProperty;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OData Category
 *
 * @author nic
 */
@EdmEntity(name="Category", key = "id", namespace = "SDL.ECommerce")
@EdmEntitySet(name="Categories")
public class ODataCategory implements Category {

    @EdmProperty(name = "id", nullable = false)
    protected String id;

    @EdmProperty(name = "name", nullable = false)
    protected String name;

    // Property only used for query based on path
    //
    @EdmProperty(name = "path")
    private String path;

    @EdmProperty(name = "categoryLink")
    private String categoryLink;

    @EdmProperty(name = "pathName")
    private String pathName;

    @EdmProperty(name = "locale")
    private String locale;

    @EdmNavigationProperty(name = "parent")
    private ODataCategory parent = null;

    @EdmNavigationProperty(name = "categories")
    private List<ODataCategory> categories = null;

    private NavigationPropertyResolver navigationPropertyResolver = null;

    public ODataCategory() {}

    public ODataCategory(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.categoryLink = category.getCategoryLink("");
        this.pathName = category.getPathName();
    }

    public void setNavigationPropertyResolver(NavigationPropertyResolver navigationPropertyResolver) {
        this.navigationPropertyResolver = navigationPropertyResolver;
    }

    @Override
    public List<Category> getCategories() {
        if ( this.categories == null && this.navigationPropertyResolver != null ) {
            this.categories = (List<ODataCategory>) this.navigationPropertyResolver.resolve(this.id, "categories");
        }
        return this.categories.stream().collect(Collectors.toList());
    }

    @Override
    public Category getParent() {
        // TODO: What happens if category does not have a parent???
        if ( this.parent == null && this.navigationPropertyResolver != null ) {
            this.parent = (ODataCategory) this.navigationPropertyResolver.resolve(this.id, "parent");
        }
        return this.parent;
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
    public String getCategoryLink(String urlPrefix) {
        return urlPrefix + this.categoryLink;
    }

    @Override
    public String getPathName() {
        return this.pathName;
    }
}
