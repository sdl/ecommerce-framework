package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.*;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OData Location
 *
 * @author nic
 */
@EdmComplex(name="Location", namespace = "SDL.ECommerce")
public class ODataLocation implements Location {

    @EdmProperty(name = "categoryRef")
    private ODataCategoryRef categoryRef;

    @EdmProperty
    private List<String> facets;

    @EdmProperty(name = "productRef")
    private ODataProductRef productRef;

    @EdmProperty(name = "staticUrl")
    private String staticUrl;

    public ODataLocation() {}
    public ODataLocation(Location location) {
        if ( location.getCategoryRef() != null ) {
            this.categoryRef = new ODataCategoryRef(location.getCategoryRef());
        }
        if ( location.getFacets() != null ) {
            this.facets = location.getFacets().stream().map(facet -> facet.toUrl()).collect(Collectors.toList());
        }
        if ( location.getProductRef() != null ) {
            this.productRef = new ODataProductRef(location.getProductRef());
        }
        this.staticUrl = location.getStaticUrl();
    }

    @Override
    public CategoryRef getCategoryRef() {
        if ( this.categoryRef != null ) {
            return this.categoryRef.getReference();
        }
        return null;
    }

    @Override
    public List<FacetParameter> getFacets() {
        if ( this.facets != null ) {
            return this.facets.stream().map(facet -> FacetParameter.fromUrl(facet)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public ProductRef getProductRef() {
        if ( this.productRef != null ) {
            return this.productRef.getReference();
        }
        return null;
    }

    @Override
    public String getStaticUrl() {
        return this.staticUrl;
    }
}
