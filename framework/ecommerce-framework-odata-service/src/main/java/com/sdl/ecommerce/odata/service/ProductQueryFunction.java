package com.sdl.ecommerce.odata.service;

import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryResult;
import com.sdl.ecommerce.api.ViewType;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.odata.datasource.ProductDataSource;
import com.sdl.ecommerce.odata.model.ODataQueryResult;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.edm.annotations.EdmFunction;
import com.sdl.odata.api.edm.annotations.EdmParameter;
import com.sdl.odata.api.edm.annotations.EdmReturnType;
import com.sdl.odata.api.processor.datasource.factory.DataSourceFactory;
import com.sdl.odata.api.service.ODataRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringTokenizer;

/**
 * Product Query Function
 *
 * @author nic
 */
@EdmFunction(
        name = "ProductQueryFunction",
        namespace = "SDL.ECommerce",
        isBound = false
)
@EdmReturnType(
        type = "SDL.ECommerce.ProductQueryResult"
)
public class ProductQueryFunction extends ECommerceOperation {

    private static final Logger LOG = LoggerFactory.getLogger(ProductQueryFunction.class);

    // Make this an entity instead???
    // ecommerce.svc/ProductQuery/

    @EdmParameter
    private String searchPhrase;

    @EdmParameter
    private String categoryId;

    @EdmParameter
    private String facets;

    @EdmParameter
    private Integer startIndex;

    @EdmParameter
    private Integer viewSize;

    @EdmParameter
    private String viewType;

    // TODO: Add filter attributes here


    @Override
    protected ODataQueryResult doECommerceOperation(ODataRequestContext oDataRequestContext, DataSourceFactory dataSourceFactory) throws ODataException {

        // TODO: Use this approach instead?? ApplicationContextProvider.getApplicationContext().getBean(TaxonomyFactory.class);

        ProductDataSource productDataSource = (ProductDataSource) dataSourceFactory.getDataSource(oDataRequestContext, "SDL.ECommerce.Product");

        Query query = this.buildQuery(productDataSource);
        QueryResult queryResult = productDataSource.getProductQueryService().query(query);
        return new ODataQueryResult(queryResult);
    }

    private Query buildQuery(ProductDataSource productDataSource) {
        Query query = productDataSource.getProductQueryService().newQuery();
        if ( this.searchPhrase != null ) {
            query.searchPhrase(searchPhrase);
        }
        if ( this.categoryId != null ) {
            Category category = productDataSource.getProductCategoryService().getCategoryById(this.categoryId);
            query.category(category);
        }
        if ( this.facets != null ) {
            StringTokenizer tokenizer = new StringTokenizer(this.facets, "=&");
            while ( tokenizer.hasMoreTokens() ) {
                String facetName = tokenizer.nextToken();
                String facetValues = tokenizer.nextToken();
                FacetParameter facet = new FacetParameter(facetName, facetValues);
                query.facet(facet);
            }
        }
        if ( this.viewType != null ) {
            query.viewType(ViewType.valueOf(this.viewType));
        }
        if ( startIndex != null ) {
            query.startIndex(startIndex);
        }
        if ( viewSize != null ) {
            query.viewSize(viewSize);
        }
        return query;
    }


}
