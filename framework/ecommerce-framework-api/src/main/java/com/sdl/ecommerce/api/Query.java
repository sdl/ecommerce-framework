package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Query
 *
 * @author nic
 */
public abstract class Query implements Cloneable {

    // TODO: Add the possiblity to attach filters that can exclude for example all products that do not have a thumbnail etc

    private Category category;
    private String searchPhrase;
    private List<FacetParameter> facets;
    private int startIndex;
    private int viewSize;
    private QueryFilter filter;
    private ViewType viewType;

    // TODO: Add Localization here as one important parameter
    // TODO: Add support for sorting

    public Query category(Category category) {
        this.category = category;
        return this;
    }

    public Query searchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
        return this;
    }

    public Query facets(List<FacetParameter> facets) {
        this.facets = facets;
        return this;
    }

    public Query facet(FacetParameter facet) {
        if ( this.facets == null ) {
            this.facets = new ArrayList<>();
        }
        this.facets.add(facet);
        return this;
    }

    public Query startIndex(int startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public Query viewSize(int viewSize) {
        this.viewSize = viewSize;
        return this;
    }

    public Query filter(QueryFilter filter) {
        this.filter = filter;
        return this;
    }

    public Query viewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }

    // Page type??? Or this a part of the query configuration???


    public Category getCategory() {
        return category;
    }

    public List<FacetParameter> getFacets() {
        return facets;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getViewSize() {
        return viewSize;
    }

    public QueryFilter getFilter() {
        return filter;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public Query clone() {
        try {
            return (Query) super.clone();
        }
        catch ( CloneNotSupportedException e ) {
            throw new RuntimeException("Could not clone E-Commerce query", e);
        }
    }
}
