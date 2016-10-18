package com.sdl.ecommerce.odata.datasource;

import com.sdl.ecommerce.odata.service.ODataRequestContextHolder;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.parser.TargetType;
import com.sdl.odata.api.processor.datasource.DataSource;
import com.sdl.odata.api.processor.datasource.DataSourceProvider;
import com.sdl.odata.api.processor.query.QueryOperation;
import com.sdl.odata.api.processor.query.strategy.QueryOperationStrategy;
import com.sdl.odata.api.service.ODataRequestContext;

/**
 * ECommerce DataSource Provider
 *
 * @author nic
 */
public abstract class ECommerceDataSourceProvider implements DataSourceProvider {

    @Override
    public DataSource getDataSource(ODataRequestContext oDataRequestContext) {
        return null;
    }

    @Override
    public QueryOperationStrategy getStrategy(ODataRequestContext oDataRequestContext, QueryOperation queryOperation, TargetType targetType) throws ODataException {
        ODataRequestContextHolder.set(oDataRequestContext);
        try {
            return this.getStrategy(queryOperation);
        }
        finally {
           ODataRequestContextHolder.clear();
        }
    }

    protected abstract QueryOperationStrategy getStrategy(QueryOperation queryOperation) throws ODataException;
}
