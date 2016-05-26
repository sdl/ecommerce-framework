package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryResult;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.QuerySuggestion;
import com.sdl.ecommerce.api.model.impl.GenericQuery;
import com.sdl.ecommerce.api.model.impl.SimpleProductDetailResult;
import com.sdl.ecommerce.odata.model.*;
import com.sdl.odata.client.BasicODataClientQuery;
import com.sdl.odata.client.FunctionImportClientQuery;
import com.sdl.odata.client.api.ODataClientQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ODataProductQueryService
 *
 * @author nic
 */
@Component
public class ODataProductQueryService implements ProductQueryService {

    @Autowired
    private ODataClient odataClient;

    @PostConstruct
    public void initialize() {
        this.odataClient.registerModelClass(ODataQueryResult.class);
        this.odataClient.registerModelClass(ODataProductSummary.class);
        this.odataClient.registerModelClass(ODataFacetGroup.class);
        this.odataClient.registerModelClass(ODataFacet.class);
        this.odataClient.registerModelClass(ODataQuerySuggestion.class);
    }

    @Override
    public Query newQuery() {
        return new GenericQuery();
    }

    @Override
    public QueryResult query(Query query) throws ECommerceException {

        FunctionImportClientQuery.Builder queryBuilder = new FunctionImportClientQuery.Builder()
                .withFunctionName("ProductQuery")
                .withEntityType(ODataProduct.class);

        if ( query.getSearchPhrase() != null ) {
            queryBuilder = queryBuilder.withFunctionParameter("searchPhrase", "'" + query.getSearchPhrase() + "'");
        }
        if ( query.getCategory() != null ) {
            queryBuilder = queryBuilder.withFunctionParameter("categoryId", "'" + query.getCategory().getId() + "'");
        }
        if ( query.getFacets() != null ) {
            StringBuilder facetsString = new StringBuilder();
            for ( int i=0; i < query.getFacets().size(); i++) {
                FacetParameter facet = query.getFacets().get(i);
                facetsString.append(facet.toUrl());
                if (i + 1 < query.getFacets().size()) {
                    facetsString.append("&");
                }
            }
            queryBuilder = queryBuilder.withFunctionParameter("facets", "'" + facetsString + "'");
        }

        ODataClientQuery oDataQuery = queryBuilder.build();
        QueryResult result = (QueryResult) this.odataClient.getEntity(oDataQuery);
        return result;
    }
}
