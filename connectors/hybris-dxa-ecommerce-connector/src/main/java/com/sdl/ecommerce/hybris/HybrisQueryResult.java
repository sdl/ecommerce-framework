package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;
import com.sdl.ecommerce.api.model.impl.GenericFacet;
import com.sdl.ecommerce.hybris.api.model.*;
import com.sdl.ecommerce.hybris.model.*;

import java.util.*;

/**
 * Hybris Query Result
 *
 * @author nic
 */
public class HybrisQueryResult implements QueryResult {

    private Query query;
    private SearchResult searchResult;
    private com.sdl.ecommerce.hybris.api.model.Category category;
    private List<Product> products;
    private ProductQueryService queryService;
    private ProductCategoryService categoryService;
    private List<String> facetIncludeList;

    /**
     * Create new Hybris query result (used for next/previous)
     * @param query
     * @param category
     */
    public HybrisQueryResult(Query query, com.sdl.ecommerce.hybris.api.model.Category category) {
        this.query = query;
        this.category = category;
        this.products = getProducts(this.category, this.query);
    }

    /**
     * Create new Hybris query result based on search results from Hybris API calls
     * @param query
     * @param searchResult
     * @param queryService
     * @param categoryService
     * @param facetIncludeList
     */
    public HybrisQueryResult(Query query,
                             SearchResult searchResult,
                             ProductQueryService queryService,
                             ProductCategoryService categoryService,
                             List<String> facetIncludeList) {
        this.query = query;
        this.products = getProducts(searchResult.getProducts());
        this.searchResult = searchResult;
        this.queryService = queryService;
        this.categoryService = categoryService;
        this.facetIncludeList = facetIncludeList;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs() {

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        Category category = this.query.getCategory();
        while ( category != null ) {
            breadcrumbs.add(0, new GenericBreadcrumb(category.getName(), category));
            category = category.getParent();
        }
        if ( this.searchResult != null ) {
            for (com.sdl.ecommerce.hybris.api.model.Breadcrumb hybrisBreadcrumb : this.searchResult.getBreadcrumbs() ) {
                if ( !hybrisBreadcrumb.getFacetCode().equals("category") ) {
                    breadcrumbs.add(new HybrisBreadcrumb(hybrisBreadcrumb));
                    //breadcrumbs.add(new HybrisBreadcrumb(hybrisBreadcrumb.getFacetValueName(), this.getFacetUrl(hybrisBreadcrumb.getRemoveQuery().getFacets(), urlPrefix), false));
                }
            }
        }
        return breadcrumbs;
    }

    @Override
    public Category getCategory() {
        return this.query.getCategory();
    }

    @Override
    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public List<Promotion> getPromotions() {
        return new ArrayList<>();
    }

    @Override
    public List<FacetGroup> getFacetGroups() {

        List<FacetGroup> facetGroups = new ArrayList<>();
        if ( this.searchResult != null ) {
            for (com.sdl.ecommerce.hybris.api.model.Facet hybrisFacet : this.searchResult.getFacets() ) {
                if ( hybrisFacet.getId().equals("category") ) {
                    FacetGroup facetGroup = new HybrisFacetGroup(hybrisFacet.getId(), hybrisFacet.getName(), true);
                    for ( FacetValue facetValue : hybrisFacet.getValues() ) {
                        Facet facet = new HybrisFacet(facetValue);
                        facetGroup.getFacets().add(facet);
                    }
                    facetGroups.add(facetGroup);
                }
                else if ( this.facetIncludeList == null || (this.facetIncludeList != null && this.facetIncludeList.contains(hybrisFacet.getId())) ) {
                    FacetGroup facetGroup = new HybrisFacetGroup(hybrisFacet.getId(), hybrisFacet.getName(), false);
                    for ( FacetValue facetValue : hybrisFacet.getValues() ) {
                        Facet facet = new HybrisFacet(facetValue);
                        facetGroup.getFacets().add(facet);
                    }
                    facetGroups.add(facetGroup);
                }
            }
        }
        // Fallback to the category if no search result
        //
        else if ( this.query.getCategory() != null ) {

            // For category pages there is no other facets available than categories
            //
            FacetGroup categoryFacetGroup = new HybrisFacetGroup(null, "Categories", true); // TODO: Have a localized version of the categories here
            facetGroups.add(categoryFacetGroup);
            for ( Category category : this.query.getCategory().getCategories() ) {

                Facet facet = new GenericFacet(category);
               // Facet facet = new HybrisFacet(category.getName(), this.linkResolver.getCategoryLink(category, urlPrefix), 0, false);
                categoryFacetGroup.getFacets().add(facet);
            }

            // TODO: Use other catalog branches as facets, such as brands ...

        }

        return facetGroups;
    }

    /**
     * Get facet URL based on Hybris facets.
     * @param facets
     * @param urlPrefix
     * @return url
     */
    // TODO: REMOVE THIS METHOD
    protected String getFacetUrl(List<FacetPair> facets, String urlPrefix) {
        String facetUrlPrefix = "";
        String facetUrl = "?";
        if ( facets.size() > 0 ) {
            Map<String, String> values = new HashMap<>();
            for (FacetPair facet : facets) {
                if ( facet.getId().equals("category") ) {
                    Category category = this.categoryService.getCategoryById(facet.getValue());
                    if ( category != null ) {
                        //facetUrlPrefix = this.linkResolver.getCategoryLink(category, urlPrefix);
                    }
                }
                else {
                    String value = values.get(facet.getId());
                    if (value != null) {
                        value += "|" + facet.getValue();
                    } else {
                        value = facet.getValue();
                    }
                    values.put(facet.getId(), value);
                }
            }
            for ( String facetId : values.keySet() ) {
                if (!facetUrl.equals("?")) {
                    facetUrl += "&";
                }
                facetUrl += facetId + "=" + values.get(facetId);
            }
        }
        return facetUrlPrefix + facetUrl;
    }

    @Override
    public int getTotalCount() {
        if ( this.category != null ) {
            return this.category.getProducts().size();
        }
        else {
            return this.searchResult.getPagination().getTotalResults();
        }
    }

    @Override
    public int getCurrentSet() {
        return (this.getStartIndex()/this.getViewSize())+1;
    }

    @Override
    public int getStartIndex() {
        return this.query.getStartIndex();
    }

    @Override
    public int getViewSize() {
        return this.query.getViewSize();
    }

    @Override
    public List<QuerySuggestion> getQuerySuggestions() {
        List<QuerySuggestion> querySuggestions = null;
        if ( searchResult.getSpellingSuggestion() != null ) {
            querySuggestions = new ArrayList<>();
            querySuggestions.add(new HybrisQuerySuggestion(searchResult));
        }
        return querySuggestions;
    }

    @Override
    public QueryResult next() throws ECommerceException {
        final Query nextQuery = this.query.clone();
        nextQuery.startIndex(nextQuery.getStartIndex()+nextQuery.getViewSize());
        if ( this.category != null ) {
            return new HybrisQueryResult(nextQuery, this.category);
        }
        else {
            return this.queryService.query(nextQuery);
        }
    }

    @Override
    public QueryResult previous() throws ECommerceException {
        final Query previousQuery = this.query.clone();
        previousQuery.startIndex(previousQuery.getStartIndex()-previousQuery.getViewSize());
        if ( this.category != null ) {
            return new HybrisQueryResult(previousQuery, this.category);
        }
        else {
            return this.queryService.query(previousQuery);
        }
    }

    @Override
    public Location getRedirectLocation() {
        return null;
    }

    private static List<Product> getProducts(com.sdl.ecommerce.hybris.api.model.Category category, Query query) {
        List<com.sdl.ecommerce.hybris.api.model.Product> hybrisProducts = category.getProducts();
        List<Product> products = new ArrayList<>();
        int viewSize = query.getViewSize();
        int startIndex = query.getStartIndex();

        if ( hybrisProducts.size() > startIndex ) {
            for ( int i=startIndex; i < (startIndex+viewSize) && i < hybrisProducts.size(); i++ ) {
                HybrisProduct product = new HybrisProduct(hybrisProducts.get(i));
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public Query getQuery() {
        return this.query;
    }

    private static List<Product> getProducts(List<com.sdl.ecommerce.hybris.api.model.Product> hybrisProducts) {
        List<Product> products = new ArrayList<>();
        for (com.sdl.ecommerce.hybris.api.model.Product hybrisProduct : hybrisProducts ) {
            products.add(new HybrisProduct(hybrisProduct));
        }
        return products;
    }
}
