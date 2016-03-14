package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.Query;

/**
 * Demandware Query
 *
 * @author nic
 */
public class DemandwareQuery extends Query {

    static final int DEFAULT_VIEW_SIZE = 20;

    public DemandwareQuery() {
        this.viewSize(DEFAULT_VIEW_SIZE);
    }

}
