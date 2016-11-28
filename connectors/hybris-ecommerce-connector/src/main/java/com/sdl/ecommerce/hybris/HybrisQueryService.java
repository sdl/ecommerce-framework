package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.hybris.api.HybrisClientImpl;
import com.sdl.ecommerce.hybris.api.HybrisClientManager;
import com.sdl.ecommerce.hybris.api.model.FacetPair;
import com.sdl.ecommerce.hybris.api.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Hybris Query Service
 *
 * @author nic
 */
@Component
public class HybrisQueryService implements ProductQueryService {

    @Autowired
    private HybrisClientManager hybrisClientManager;

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private ProductCategoryService categoryService;

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

        SearchResult result = this.hybrisClientManager.getInstance().search(query.getSearchPhrase(), query.getViewSize(), currentPage, sort, facets);

        List<String> facetIncludeList = null;
        if ( query.getViewType() == ViewType.FLYOUT ) {
           facetIncludeList = this.getFlyoutFacets();
        }
        return new HybrisQueryResult(query, result, this, this.categoryService, facetIncludeList);

    }

    private List<String> getFlyoutFacets() {
        String flyoutFacetsString = this.localizationService.getLocalizedConfigProperty("hybris-flyoutFacets");
        if ( flyoutFacetsString != null && !flyoutFacetsString.isEmpty() ) {
            List<String> flyoutFacets = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(flyoutFacetsString, ", ");
            while ( tokenizer.hasMoreTokens() ) {
                flyoutFacets.add(tokenizer.nextToken());
            }
            return flyoutFacets;
        }
        return null;
    }

}
