package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.api.model.FacetGroup;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OData Facet Group
 *
 * @author nic
 */
@EdmComplex(name="FacetGroup", namespace = "SDL.ECommerce")
public class ODataFacetGroup implements FacetGroup {

    @EdmProperty
    private String id;

    @EdmProperty
    private String title;

    @EdmProperty
    private String type;

    @EdmProperty
    private boolean isCategory;

    @EdmProperty
    private List<ODataFacet> facets;

    public ODataFacetGroup() {}
    public ODataFacetGroup(FacetGroup facetGroup) {
        this.id = facetGroup.getId();
        this.title = facetGroup.getTitle();
        this.type = facetGroup.getType();
        this.isCategory = facetGroup.isCategory();
        if ( facetGroup.getFacets() != null ) {
            facets = new ArrayList<>();
            facetGroup.getFacets().forEach(facet -> this.facets.add(new ODataFacet(facet)));
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean isCategory() {
        return this.isCategory;
    }

    @Override
    public List<Facet> getFacets() {
        return this.facets.stream().collect(Collectors.toList());
    }
}
