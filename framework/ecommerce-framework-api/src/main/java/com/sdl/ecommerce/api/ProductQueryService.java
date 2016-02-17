package com.sdl.ecommerce.api;

/**
 * Product Query Service
 *
 * Queries upon categories and/or search phrases.
 * Will return a set of products, facets, breadcrumbs and possible promotions.
 *
 * @author nic
 */
public interface ProductQueryService {

    /**
     * Create new query
     * @return query
     */
    Query newQuery();

    /**
     * Submit the query to the query service
     * @param query
     * @return query result
     * @throws ECommerceException
     */
    QueryResult query(Query query) throws ECommerceException;


}
