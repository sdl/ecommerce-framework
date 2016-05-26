package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Facet;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * OData Facet
 *
 * @author nic
 */
@EdmComplex(name="Facet", namespace = "SDL.ECommerce")
public class ODataFacet implements Facet {

    @EdmProperty
    private String title;

    // TODO: Have a link strategy to get the URL here
    @EdmProperty
    private String url;

    @EdmProperty
    private int count;

    @EdmProperty
    private boolean isSelected;

    public ODataFacet() {}
    public ODataFacet(Facet facet) {
        this.title = facet.getTitle();
        this.url = facet.getUrl();
        this.count = facet.getCount();
        this.isSelected = facet.isSelected();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }
}
