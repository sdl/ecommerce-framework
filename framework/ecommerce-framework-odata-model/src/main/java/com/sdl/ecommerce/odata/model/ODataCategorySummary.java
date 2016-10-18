package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.List;

/**
 * OData Category Summary
 *
 * @author nic
 */
@EdmComplex(name="CategorySummary", namespace = "SDL.ECommerce")
public class ODataCategorySummary implements Category {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String pathName;

    public ODataCategorySummary() {}
    public ODataCategorySummary(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.pathName = category.getPathName();
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
    public String getPathName() {
        return this.pathName;
    }

    /******** Not available in the category summary ***************/

    @Override
    public Category getParent() {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }


}
