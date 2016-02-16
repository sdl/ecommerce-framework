package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.*;

import java.util.List;

/**
 * E-Commerce Result Set
 *
 * @author nic
 */
public interface QueryResult extends ECommerceResult {

    Category getCategory();

    List<Product> getProducts();

    List<FacetGroup> getFacetGroups(String urlPrefix);

    int getTotalCount();

    int getStartIndex();

    int getViewSize();

    int getCurrentSet();

    List<QuerySuggestion> getQuerySuggestions();

    QueryResult next() throws ECommerceException;

    QueryResult previous() throws ECommerceException;

    String getRedirectUrl();

    Query getQuery();

}
