package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.CategoryRef;
import com.sdl.ecommerce.api.model.Facet;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * OData Breadcrumb
 *
 * @author nic
 */
@EdmComplex(name="Breadcrumb", namespace = "SDL.ECommerce")
public class ODataBreadcrumb implements Breadcrumb {

    @EdmProperty
    private String title;

    @EdmProperty
    private boolean isCategory;

    @EdmProperty
    private ODataFacet facet;

    @EdmProperty
    private ODataCategoryRef categoryRef;

    public ODataBreadcrumb() {}

    public ODataBreadcrumb(Breadcrumb breadcrumb) {
        this.title = breadcrumb.getTitle();
        this.isCategory = breadcrumb.isCategory();
        if ( breadcrumb.getCategoryRef() != null ) {
            this.categoryRef = new ODataCategoryRef(breadcrumb.getCategoryRef());
        }
        if ( breadcrumb.getFacet() != null ) {
            this.facet = new ODataFacet(breadcrumb.getFacet());
        }
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean isCategory() {
        return isCategory;
    }

    @Override
    public CategoryRef getCategoryRef() {
        return this.categoryRef.getReference();
    }

    @Override
    public Facet getFacet() {
        return this.facet;
    }
}
