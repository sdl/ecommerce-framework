package com.sdl.ecommerce.api;

/**
 * Query Input Contributor
 * - Is used to go through all widgets on the page to gather all input from them to the product query
 *
 * @author nic
 */
public interface QueryInputContributor {

    void contributeToQuery(Query query);
}
