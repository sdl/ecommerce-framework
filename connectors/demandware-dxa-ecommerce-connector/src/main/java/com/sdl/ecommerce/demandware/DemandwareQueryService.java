package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientImpl;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
import com.sdl.ecommerce.demandware.api.model.ProductSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Demandware Query Service
 *
 * @author nic
 */
@Component
public class DemandwareQueryService implements ProductQueryService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private DemandwareShopClientManager shopClientManager;

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private ECommerceLinkResolver linkResolver;

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
        ProductSearchResult searchResult = this.shopClientManager.getInstance().search(query.getSearchPhrase(), categoryId, query.getStartIndex(), query.getViewSize(), refinements);
        List<String> facetIncludeList = null;
        if ( query.getViewType() == ViewType.FLYOUT ) {
            facetIncludeList = this.getFlyoutFacets();
        }
        return new DemandwareQueryResult(query, searchResult, this.shopClientManager.getInstance(), this.categoryService, facetIncludeList, this.linkResolver);
    }

    /**
     * Get list of all facets that should be included in the flyout menu.
     * @return list of facet names
     */
    private List<String> getFlyoutFacets() {
        String flyoutFacetsString = this.localizationService.getLocalizedConfigProperty("demandware-flyoutFacets");
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
