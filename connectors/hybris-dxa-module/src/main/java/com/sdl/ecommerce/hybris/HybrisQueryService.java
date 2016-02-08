package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.hybris.api.HybrisClient;
import com.sdl.ecommerce.hybris.api.model.Category;
import com.sdl.ecommerce.hybris.api.model.FacetPair;
import com.sdl.ecommerce.hybris.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * HybrisQueryService
 *
 * @author nic
 */
@Component
public class HybrisQueryService implements ProductQueryService {

    @Autowired
    private HybrisClient hybrisClient;

    @Autowired
    private ProductCategoryService categoryService;

    @Value("${hybris.flyoutFacets}")
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
        return new HybrisQuery();
    }

    @Override
    public QueryResult query(Query query) throws ECommerceException {

        int currentPage = query.getStartIndex() == 0 ? 0 : query.getStartIndex() / query.getViewSize();
        String sort = null; // TODO: IMPLEMENT
        List<FacetPair> facets = new ArrayList<>();
        if (query.getCategory() != null) {
            facets.add(new FacetPair("category", query.getCategory().getId()));
        }
        if (query.getFacets() != null) {
            for (FacetParameter facetParameter : query.getFacets()) {
                for (String facetValue : facetParameter.getValues()) {
                    facets.add(new FacetPair(facetParameter.getName(), facetValue));
                }
            }
        }

        SearchResult result = this.hybrisClient.search(query.getSearchPhrase(), query.getViewSize(), currentPage, sort, facets);
        if ( query.getViewType() == ViewType.FLYOUT ) {
            // Remove all facets that are not configured in the flyout facet list
            //
            for ( int i=0; i < result.getFacets().size(); i++ ) {

            }
        }
        return new HybrisQueryResult(query, result, this, this.categoryService, this.flyoutFacetList);

    }



}
