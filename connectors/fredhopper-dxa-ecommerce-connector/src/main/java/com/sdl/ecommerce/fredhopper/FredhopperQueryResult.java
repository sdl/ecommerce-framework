package com.sdl.ecommerce.fredhopper;

import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.ViewType;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.QuerySuggestion;
import com.sdl.ecommerce.fredhopper.model.*;
import com.sdl.ecommerce.fredhopper.model.promotion.FredhopperPromotion;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Fredhopper Result Set
 *
 * @author nic
 */
public class FredhopperQueryResult extends FredhopperResultBase implements QueryResult {

    private Query query;
    private Category category;

    public FredhopperQueryResult(Page fredhopperPage, Query query, FredhopperLinkManager linkManager) {
        super(fredhopperPage, linkManager);
        this.query = query;
        this.category = query.getCategory();
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public List<Product> getProducts() {
        ItemsSection itemsSection = universe.getItemsSection();
        return this.getProducts(itemsSection.getItems().getItem());
    }

    @Override
    public List<Promotion> getPromotions() {
        if ( query.getViewType() == ViewType.FLYOUT ) {
            return this.getPromotions(this.universe, "flyout", "yes");
        }
        else {
            return this.getPromotions(this.universe);
        }
    }

    @Override
    public List<FacetGroup> getFacetGroups(String urlPrefix) {
        if ( urlPrefix == null ) { urlPrefix = ""; }
        if ( query.getViewType() == ViewType.FLYOUT ) {
            return this.getFacetGroups(this.universe, query.getFacets(), urlPrefix, this.category.getCategoryLink(urlPrefix), "flyout", "yes");
        }
        else {
            return this.getFacetGroups(this.universe, query.getFacets(), urlPrefix);
        }
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {
        if ( urlPrefix == null ) { urlPrefix = ""; }
        return this.getBreadcrumbs(this.universe, urlPrefix, rootTitle, query.getFacets());
    }

    @Override
    public int getTotalCount() {
        return this.universe.getItemsSection().getResults().getTotalItems();
    }

    @Override
    public int getCurrentSet() {
        return this.universe.getItemsSection().getResults().getCurrentSet();
    }

    @Override
    public int getStartIndex() {
        return this.universe.getItemsSection().getResults().getStartIndex();
    }

    @Override
    public int getViewSize() {
        return this.universe.getItemsSection().getResults().getViewSize();
    }

    @Override
    public List<QuerySuggestion> getQuerySuggestions() {
        List<QuerySuggestion> querySuggestions =null;
        if ( this.universe.getQueryAlternatives() != null ) {
            querySuggestions = new ArrayList<>();
            for (com.fredhopper.webservice.client.QuerySuggestion querySuggestion : this.universe.getQueryAlternatives().getQuerySuggestion() ) {
                querySuggestions.add(new FredhopperQuerySuggestion(querySuggestion));
            }
        }
        return querySuggestions;
    }

    @Override
    public QueryResult next() throws ECommerceException {
        final Query nextQuery = this.query.clone();
        nextQuery.startIndex(nextQuery.getStartIndex()+nextQuery.getViewSize());
        return this.queryService.query(nextQuery);
    }

    @Override
    public QueryResult previous() throws ECommerceException {
        final Query previousQuery = this.query.clone();
        previousQuery.startIndex(previousQuery.getStartIndex()-previousQuery.getViewSize());
        return this.queryService.query(previousQuery);
    }

    @Override
    public String getRedirectUrl() {
        if ( this.fredhopperPage.getRedirect() != null ) {
            return this.fredhopperPage.getRedirect().getRedirectUrl();
        }
        if ( this.fredhopperPage.getInfo().getView() == com.fredhopper.webservice.client.ViewType.DETAIL ) {
            List<Product> products = this.getProducts();
            if ( products.size() == 1 ) {
                return products.get(0).getDetailPageUrl();
            }
        }
        return null;
    }

    @Override
    public Query getQuery() {
        return this.query;
    }
}
