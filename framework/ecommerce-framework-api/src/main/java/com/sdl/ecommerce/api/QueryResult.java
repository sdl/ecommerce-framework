package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.*;

import java.util.List;

/**
 * E-Commerce Result Set
 *
 * @author nic
 */
public interface QueryResult extends ECommerceResult {

    /**
     * @return current category (if any)
     */
    Category getCategory();

    /**
     * @return list of matching products
     */
    List<Product> getProducts();

    /**
     * Get a list of facet groups where links is using provided URL prefix
     * @param urlPrefix
     * @return list of facet groups
     */
    // TODO: Factor away the URL prefix here!! Can link strategy be used here??
    List<FacetGroup> getFacetGroups(String urlPrefix);

    /**
     * @return total number of hits in the query
     */
    int getTotalCount();

    /**
     * @return start index in the total search result
     */
    int getStartIndex();

    /**
     * @return view size of the current result set
     */
    int getViewSize();

    /**
     * @return current result set (first, second, third etc)
     */
    int getCurrentSet();

    /**
     * @return list of possible query suggestions on current search phrase
     */
    List<QuerySuggestion> getQuerySuggestions();

    /**
     * @return next result set
     * @throws ECommerceException
     */
    QueryResult next() throws ECommerceException;

    /**
     * @return previous result set
     * @throws ECommerceException
     */
    QueryResult previous() throws ECommerceException;

    /**
     * @return redirect URL triggered by a specific combination of search phrase, category, facet etc
     */
    String getRedirectUrl();

    /**
     * @return current query that resulted in this result
     */
    Query getQuery();

}
