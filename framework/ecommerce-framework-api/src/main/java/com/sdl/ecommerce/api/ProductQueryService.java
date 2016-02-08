package com.sdl.ecommerce.api;

/**
 * ProductQueryService
 *
 * @author nic
 */
public interface ProductQueryService {

    Query newQuery();

    QueryResult query(Query query) throws ECommerceException;


}
