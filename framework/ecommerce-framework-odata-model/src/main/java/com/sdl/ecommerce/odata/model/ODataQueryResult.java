package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryResult;
import com.sdl.ecommerce.api.model.*;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OData Query Result
 *
 * @author nic
 */
@EdmEntity(name="ProductQueryResult", key ="resultId", namespace = "SDL.ECommerce")
@EdmEntitySet(name="ProductQueryResults")
public class ODataQueryResult implements QueryResult {

    @EdmProperty
    private String resultId;

    @EdmProperty
    private int totalCount;

    @EdmProperty
    private int startIndex;

    @EdmProperty
    private int viewSize;

    @EdmProperty
    private int currentSet;

    @EdmProperty
    private ODataLocation redirectLocation;

    @EdmProperty
    private List<ODataQuerySuggestion> querySuggestions = new ArrayList<>();

    @EdmProperty
    private List<ODataFacetGroup> facetGroups = new ArrayList<>();

    @EdmProperty
    private List<ODataProductSummary> products = new ArrayList<>();

    @EdmProperty
    private List<ODataPromotion> promotions = new ArrayList<>();

    @EdmProperty
    private List<ODataBreadcrumb> breadcrumbs = new ArrayList<>();

    public ODataQueryResult() {}

    public ODataQueryResult(QueryResult queryResult) {
        this.resultId = Integer.toString(queryResult.hashCode());
        queryResult.getProducts().forEach(product -> products.add(new ODataProductSummary(product)));
        this.totalCount = queryResult.getTotalCount();
        this.currentSet = queryResult.getCurrentSet();
        this.startIndex = queryResult.getStartIndex();
        this.viewSize = queryResult.getViewSize();
        if ( queryResult.getRedirectLocation() != null ) {
            this.redirectLocation = new ODataLocation(queryResult.getRedirectLocation());
        }
        if ( queryResult.getQuerySuggestions() != null ) {
            queryResult.getQuerySuggestions().forEach(querySuggestion -> this.querySuggestions.add(new ODataQuerySuggestion(querySuggestion)));
        }

        List<FacetGroup> facetGroups = queryResult.getFacetGroups();
        if ( facetGroups != null ) {
            facetGroups.forEach(facetGroup -> this.facetGroups.add(new ODataFacetGroup(facetGroup)));
        }
        if ( queryResult.getPromotions() != null ) {
            queryResult.getPromotions().forEach(promotion -> this.promotions.add(new ODataPromotion(promotion)));
        }
        if ( queryResult.getBreadcrumbs() != null ) {
            queryResult.getBreadcrumbs().forEach(breadcrumb -> this.breadcrumbs.add(new ODataBreadcrumb(breadcrumb)));
        }
    }

    @Override
    public Category getCategory() {
        // TODO: NOT NEEDED. Can be filled by the product query service
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return this.products.stream().collect(Collectors.toList());
    }

    @Override
    public List<FacetGroup> getFacetGroups() {
        List<FacetGroup> facetGroups = this.facetGroups.stream().collect(Collectors.toList());
        return facetGroups;
    }

    @Override
    public int getTotalCount() {
        return this.totalCount;
    }

    @Override
    public int getStartIndex() {
        return this.startIndex;
    }

    @Override
    public int getViewSize() {
        return this.viewSize;
    }

    @Override
    public int getCurrentSet() {
        return this.currentSet;
    }

    @Override
    public List<QuerySuggestion> getQuerySuggestions() {
        return this.querySuggestions.stream().collect(Collectors.toList());
    }

    @Override
    public QueryResult next() throws ECommerceException {
        // TODO: IMPLEMENT!!!
        return null;
    }

    @Override
    public QueryResult previous() throws ECommerceException {
        // TODO: IMPLEMENT
        return null;
    }

    @Override
    public Location getRedirectLocation() {
        return this.redirectLocation;
    }

    @Override
    public Query getQuery() {
        // TODO: Skip serializing this one....
        return null;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        // TODO: Will this work for JDK7 clients??
        return this.breadcrumbs.stream().collect(Collectors.toList());
    }

    @Override
    public List<Promotion> getPromotions() {
        return this.promotions.stream().map(promotion -> promotion.toPromotion()).collect(Collectors.toList());
    }
}
