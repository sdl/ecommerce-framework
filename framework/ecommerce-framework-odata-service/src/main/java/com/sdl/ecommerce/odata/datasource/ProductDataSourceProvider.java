package com.sdl.ecommerce.odata.datasource;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.odata.model.ODataProduct;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.processor.datasource.DataSource;
import com.sdl.odata.api.processor.datasource.ODataDataSourceException;
import com.sdl.odata.api.processor.query.QueryOperation;
import com.sdl.odata.api.processor.query.SelectByKeyOperation;
import com.sdl.odata.api.processor.query.strategy.QueryOperationStrategy;
import com.sdl.odata.api.service.ODataRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Product DataSource Provider
 *
 * @author nic
 */
@Component
public class ProductDataSourceProvider extends ECommerceDataSourceProvider {

    @Autowired
    private ProductDataSource productDataSource;

    @Override
    public boolean isSuitableFor(ODataRequestContext oDataRequestContext, String entityType) throws ODataDataSourceException {
        return oDataRequestContext.getEntityDataModel().getType(entityType).getJavaType().equals(ODataProduct.class);
    }

    @Override
    public DataSource getDataSource(ODataRequestContext oDataRequestContext) {
        return this.productDataSource;
    }

    @Override
    public QueryOperationStrategy getStrategy(QueryOperation queryOperation) throws ODataException {

        List<ODataProduct> products = new ArrayList<>();

        if ( queryOperation instanceof SelectByKeyOperation) {
            Map<String, Object> keys = ((SelectByKeyOperation) queryOperation).getKeyAsJava();
            String id = (String)keys.get("id");
            ProductDetailResult result = this.productDataSource.getProductDetailService().getDetail(id);
            if ( result != null && result.getProductDetail() != null ) {
                products.add(new ODataProduct(result.getProductDetail()));
            }
        }

        // TODO: Have a different model for products when query listers???

        return () -> {
            return /*QueryResult.from(*/products;
        };
    }
}
