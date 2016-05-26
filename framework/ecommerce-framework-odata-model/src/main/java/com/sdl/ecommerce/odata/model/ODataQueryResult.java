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
    private String redirectUrl;

    @EdmProperty
    private List<ODataQuerySuggestion> querySuggestions = new ArrayList<>();

    @EdmProperty
    private List<ODataFacetGroup> facetGroups = new ArrayList<>();

    @EdmProperty
    private List<ODataProductSummary> products = new ArrayList<>();

    public ODataQueryResult() {}

    public ODataQueryResult(QueryResult queryResult) {
        queryResult.getProducts().forEach(product -> products.add(new ODataProductSummary(product)));
        this.totalCount = queryResult.getTotalCount();
        this.currentSet = queryResult.getCurrentSet();
        this.startIndex = queryResult.getStartIndex();
        this.viewSize = queryResult.getViewSize();
        this.redirectUrl = queryResult.getRedirectUrl();
        if ( queryResult.getQuerySuggestions() != null ) {
            queryResult.getQuerySuggestions().forEach(querySuggestion -> this.querySuggestions.add(new ODataQuerySuggestion(querySuggestion)));
        }

        // Temporary solution
        //
        List<FacetGroup> facetGroups = queryResult.getFacetGroups("");
        if ( facetGroups != null ) {
            facetGroups.forEach(facetGroup -> this.facetGroups.add(new ODataFacetGroup(facetGroup)));
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
    public List<FacetGroup> getFacetGroups(String urlPrefix) {
        // TODO: Hook this into a link strategy!!!
        // Return something normalized tere
        return this.facetGroups.stream().collect(Collectors.toList());
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
        return null;
    }

    @Override
    public QueryResult previous() throws ECommerceException {
        return null;
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public Query getQuery() {
        // TODO: Skip serializing this one....
        return null;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {
        // TODO: Do something smart here like have regex for url prefix & root title
        return null;
    }

    @Override
    public List<Promotion> getPromotions() {
        // TODO: How to handle promotion sub classes???
        return null;
    }
}
