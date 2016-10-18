package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.CategoryRef;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataCategoryRef
 *
 * @author nic
 */
@EdmComplex(name = "CategoryRef", namespace = "SDL.ECommerce")
public class ODataCategoryRef {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String path;

    public ODataCategoryRef() {}
    public ODataCategoryRef(CategoryRef categoryRef) {
        this.id = categoryRef.getId();
        this.name = categoryRef.getName();
        this.path = categoryRef.getPath();
    }

    public CategoryRef getReference() {
        return new CategoryRef(this.id, this.name, this.path);
    }
}
