package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FredhopperQueryService
 *
 * @author nic
 */
@Component
public class FredhopperQueryService implements ProductQueryService {

    // TODO: Use Spring qualifier here to specify what combo I would like to use???


    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private FredhopperClient fredhopperClient;

    @Override
    public Query newQuery() {
        return new FredhopperQuery();
    }

    @Override
    public QueryResult query(Query query) throws ECommerceException {
        QueryResult result = this.fredhopperClient.query(query);
        this.injectServices(result);
        return result;
    }

    private void injectServices(QueryResult result) {
        FredhopperQueryResult fredhopperResult = (FredhopperQueryResult) result;
        fredhopperResult.setQueryService(this);
        fredhopperResult.setCategoryService(this.categoryService);
    }
}
