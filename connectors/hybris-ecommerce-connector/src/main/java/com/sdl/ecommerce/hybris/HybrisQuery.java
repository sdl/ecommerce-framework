package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.Query;

/**
 * Hybris Query
 *
 * @author nic
 */
public class HybrisQuery extends Query {

    static final int DEFAULT_VIEW_SIZE = 20;

    public HybrisQuery() {
        this.viewSize(DEFAULT_VIEW_SIZE);
    }
}
