package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryResult;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;
import com.sdl.ecommerce.api.model.impl.GenericFacet;
import com.sdl.ecommerce.api.model.impl.GenericFacetGroup;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
import com.sdl.ecommerce.demandware.api.model.ProductSearchHit;
import com.sdl.ecommerce.demandware.api.model.ProductSearchRefinement;
import com.sdl.ecommerce.demandware.api.model.ProductSearchRefinementValue;
import com.sdl.ecommerce.demandware.api.model.ProductSearchResult;
import com.sdl.ecommerce.demandware.model.DemandwareProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Demandware Query Result
 *
 * @author nic
 */
public class DemandwareQueryResult implements QueryResult {

    private Query query;
    private Category category;
    private ProductSearchResult searchResult;
    private DemandwareShopClient shopClient;
    private ProductCategoryService categoryService;
    private List<String> facetIncludeList;

    public DemandwareQueryResult(Query query,
                                 ProductSearchResult searchResult,
                                 DemandwareShopClient shopClient,
                                 ProductCategoryService categoryService,
                                 List<String> facetIncludeList) {

        this.query = query;
        this.category = this.query.getCategory();
        this.searchResult = searchResult;
        this.shopClient = shopClient;
        this.categoryService = categoryService;
        this.facetIncludeList = facetIncludeList;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if ( this.searchResult.getHits() != null ) {
            for (ProductSearchHit searchHit : this.searchResult.getHits()) {
                products.add(new DemandwareProduct(searchHit));
            }
        }
        return products;
    }

    @Override
    public List<FacetGroup> getFacetGroups(String urlPrefix) {
        List<FacetGroup> facetGroups = new ArrayList<>();
        for ( ProductSearchRefinement refinement : this.searchResult.getRefinements() ) {
            boolean isCategory = refinement.getAttribute_id().equals("cgid");
            if ( refinement.getValues() == null ||
                    ( !isCategory && this.facetIncludeList != null && !this.facetIncludeList.contains(refinement.getAttribute_id()) ) ) {
                continue;
            }
            FacetGroup facetGroup = new GenericFacetGroup(refinement.getAttribute_id(), refinement.getLabel(), isCategory);
            if ( isCategory ) {
                List<ProductSearchRefinementValue> categoryRefinementValues;
                if ( this.category != null ) {
                    ProductSearchRefinementValue categoryValue = this.findCurrentCategoryRefinement(refinement.getValues(), category.getId());
                    categoryRefinementValues = categoryValue.getValues();
                }
                else {
                    categoryRefinementValues = refinement.getValues();
                }
                if ( categoryRefinementValues != null ) {
                    for (ProductSearchRefinementValue refinementValue : categoryRefinementValues) {
                        if (refinementValue.getHit_count() == 0) {
                            // Skip all category facet values with no hits
                            //
                            continue;
                        }
                        Facet facet = new GenericFacet(refinementValue.getLabel(), this.getFacetUrl(refinement.getAttribute_id(), refinementValue.getValue(), urlPrefix), refinementValue.getHit_count(), false);
                        facetGroup.getFacets().add(facet);
                    }
                }
            }
            else {
                // If facet
                //
                for (ProductSearchRefinementValue refinementValue : refinement.getValues()) {
                    if (refinementValue.getHit_count() == 0) {
                        // Skip all facet values with no hits
                        //
                        continue;
                    }
                    /*
                    else if ( isCategory && this.category != null && refinementValue.getValue().equals(this.category.getId()) ) {
                        // TODO: Use all sub facets for the category!!!
                        continue;
                    }
                    */
                    if (isCategory && this.category != null && refinementValue.getValue().equals(this.category.getId())) {
                        // Iterate
                    }

                    boolean isSelected = false;
                    if (this.searchResult.getSelected_refinements() != null) {
                        String selectedValues = this.searchResult.getSelected_refinements().get(refinement.getAttribute_id());
                        isSelected = selectedValues != null && selectedValues.contains(refinementValue.getValue());
                    }
                    Facet facet = new GenericFacet(refinementValue.getLabel(), this.getFacetUrl(refinement.getAttribute_id(), refinementValue.getValue(), urlPrefix), refinementValue.getHit_count(), isSelected);
                    facetGroup.getFacets().add(facet);
                }
            }
            if ( facetGroup.getFacets().size() > 0 ) {
                facetGroups.add(facetGroup);
            }
        }
        return facetGroups;
    }

    protected ProductSearchRefinementValue findCurrentCategoryRefinement(List<ProductSearchRefinementValue> values, String categoryId) {
        for ( ProductSearchRefinementValue value : values ) {
            if ( value.getValue().equals(categoryId) ) {
                return value;
            }
            else if ( value.getValues() != null ) {
                ProductSearchRefinementValue subValue = findCurrentCategoryRefinement(value.getValues(), categoryId);
                if ( subValue != null ) {
                    return subValue;
                }
            }
        }
        return null;
    }

    protected String getFacetUrl(String refinementId, String refinementValue, String urlPrefix) {
        String facetUrlPrefix = "";
        String facetUrl = "?";
        boolean isIncludedInMultivalue = false;
        if ( this.searchResult.getSelected_refinements() != null ) {
            for (String selectedRefinement : this.searchResult.getSelected_refinements().keySet()) {
                if (selectedRefinement.equals("cgid") /*|| selectedRefinement.equals(excludeRefinementId)*/) {
                    continue;
                }
                String selectedRefinementValue = this.searchResult.getSelected_refinements().get(selectedRefinement);
                if (!facetUrl.equals("?")) {
                    facetUrl += "&";
                }
                facetUrl += selectedRefinement + "=" + selectedRefinementValue; // Can contain multi values separated with '|'
                if (selectedRefinement.equals(refinementId)) {
                    facetUrl += "|" + refinementValue;
                    isIncludedInMultivalue = true;
                }
            }
        }
        if ( refinementId.equals("cgid") ) {
            Category category = this.categoryService.getCategoryById(refinementValue);
            if ( category != null ) {
                facetUrlPrefix = category.getCategoryLink(urlPrefix);
            }
        }
        else if (!isIncludedInMultivalue ) {
            if ( !facetUrl.equals("?") ) {
                facetUrl += "&";
            }
            facetUrl += refinementId + "=" + refinementValue;
        }
        if ( facetUrlPrefix.length() == 0 && this.category != null ) {
            facetUrlPrefix = this.category.getCategoryLink(urlPrefix);
        }
        return facetUrlPrefix + facetUrl;
    }

    protected String getRemoveFacetUrl(String refinementId, String refinementValue) {
        String facetUrlPrefix = "";
        String facetUrl = "?";
        if ( this.searchResult.getSelected_refinements() != null ) {
            for (String selectedRefinement : this.searchResult.getSelected_refinements().keySet()) {
                if (selectedRefinement.equals("cgid") ) {
                    continue;
                }
                String selectedRefinementValue = this.searchResult.getSelected_refinements().get(selectedRefinement);

                String processedRefinedValue;
                if ( selectedRefinement.equals(refinementId) ) {
                    StringTokenizer tokenizer = new StringTokenizer(selectedRefinementValue, "|");
                    processedRefinedValue = "";
                    while ( tokenizer.hasMoreTokens() ) {
                        String value = tokenizer.nextToken();
                        if ( !value.equals(refinementValue) ) {
                            if ( processedRefinedValue.length() > 0 ) {
                                processedRefinedValue += "|";
                            }
                            processedRefinedValue += value;
                        }
                    }
                }
                else {
                    processedRefinedValue = selectedRefinementValue;
                }
                if ( processedRefinedValue.length() > 0 ) {
                    if ( !facetUrl.equals("?") ) {
                        facetUrl += "&";
                    }
                    facetUrl += selectedRefinement + "=" + processedRefinedValue;
                }
            }
        }

        return facetUrlPrefix + facetUrl;
    }

    @Override
    public int getTotalCount() {
        return this.searchResult.getTotal();
    }

    @Override
    public int getStartIndex() {
        return this.searchResult.getStart();
    }

    @Override
    public int getViewSize() {
        return this.query.getViewSize();
    }

    @Override
    public int getCurrentSet() {
        return (this.getStartIndex()/this.getViewSize())+1;
    }

    @Override
    public List<QuerySuggestion> getQuerySuggestions() {
        return null;
    }

    @Override
    public QueryResult next() throws ECommerceException {
        ProductSearchResult nextResult = this.shopClient.getNext(this.searchResult);
        Query newQuery = this.query.clone();
        newQuery.startIndex(nextResult.getStart());
        return new DemandwareQueryResult(newQuery, nextResult, this.shopClient, this.categoryService, this.facetIncludeList);
    }

    @Override
    public QueryResult previous() throws ECommerceException {
        ProductSearchResult previousResult = this.shopClient.getPrevious(this.searchResult);
        Query newQuery = this.query.clone();
        newQuery.startIndex(previousResult.getStart());
        return new DemandwareQueryResult(newQuery, previousResult, this.shopClient, this.categoryService, this.facetIncludeList);
    }

    @Override
    public String getRedirectUrl() {
        return null;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        Category category = this.category;
        while ( category != null ) {
            breadcrumbs.add(0, new GenericBreadcrumb(category.getName(), category.getCategoryLink(urlPrefix), true));
            category = category.getParent();
        }
        if ( rootTitle != null ) {
            breadcrumbs.add(0, new GenericBreadcrumb(rootTitle, urlPrefix, true));
        }
        if ( this.searchResult.getSelected_refinements() != null ) {
            for ( String selectedRefinement : this.searchResult.getSelected_refinements().keySet() ) {
                if ( !selectedRefinement.equals("cgid") ) {
                    ProductSearchRefinement refinement = this.getRefinementById(selectedRefinement);
                    String selectedRefinementValue = this.searchResult.getSelected_refinements().get(selectedRefinement);

                    if ( refinement != null ) {
                        StringTokenizer tokenizer = new StringTokenizer(selectedRefinementValue, "|");
                        while ( tokenizer.hasMoreTokens() ) {
                            String value = tokenizer.nextToken();
                            ProductSearchRefinementValue refinementValue = this.getRefinementValue(refinement, value);
                            breadcrumbs.add(new GenericBreadcrumb(refinementValue.getLabel(), this.getRemoveFacetUrl(refinement.getAttribute_id(), value), false));
                        }
                    }
                }
            }
        }
        return breadcrumbs;
    }

    @Override
    public Query getQuery() {
        return this.query;
    }

    private ProductSearchRefinement getRefinementById(String id) {
        for ( ProductSearchRefinement refinement : this.searchResult.getRefinements() ) {
            if ( refinement.getAttribute_id().equals(id) ) {
                return refinement;
            }
        }
        return null;
    }

    private ProductSearchRefinementValue getRefinementValue(ProductSearchRefinement refinement, String value) {
        for ( ProductSearchRefinementValue refinementValue : refinement.getValues() ) {
            if ( refinementValue.getValue().equals(value) ) {
                return refinementValue;
            }
        }
        return null;
    }

    @Override
    public List<Promotion> getPromotions() {
        return new ArrayList<>();
    }
}
