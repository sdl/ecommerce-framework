package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
import com.sdl.ecommerce.demandware.api.model.ProductSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * DemandwareQueryService
 *
 * @author nic
 */
@Component
public class DemandwareQueryService implements ProductQueryService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private DemandwareShopClient shopClient;

    @Value("${demandware.flyoutFacets}")
    private String flyoutFacets = "";
    private List<String> flyoutFacetList = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        StringTokenizer tokenizer = new StringTokenizer(flyoutFacets, ", ");
        while ( tokenizer.hasMoreTokens() ) {
            this.flyoutFacetList.add(tokenizer.nextToken());
        }
    }

    @Override
    public Query newQuery() {
        return new DemandwareQuery();
    }

    @Override
    public QueryResult query(Query query) throws ECommerceException {

        String categoryId = query.getCategory() != null ? query.getCategory().getId() : null;

        Map<String,String> refinements = null;
        if ( query.getFacets() != null ) {
            refinements = new HashMap<>();
            for ( FacetParameter facet : query.getFacets() ) {

                // Demandware only support multi-value and not ranges etc
                //
                StringBuilder refinementValue = new StringBuilder();
                for ( String value : facet.getValues() ) {
                    if ( refinementValue.length() != 0 ) {
                        refinementValue.append("|");
                    }
                    refinementValue.append(value);
                }
                refinements.put(facet.getName(), refinementValue.toString());
            }
        }
        ProductSearchResult searchResult = this.shopClient.search(query.getSearchPhrase(), categoryId, query.getStartIndex(), query.getViewSize(), refinements);
        List<String> facetIncludeList = null;
        if ( query.getViewType() == ViewType.FLYOUT ) {
            facetIncludeList = this.flyoutFacetList;
        }
        return new DemandwareQueryResult(query.getCategory(), searchResult, this.shopClient, this.categoryService, query.getViewSize(), facetIncludeList);
    }
}
