package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.Query;
import com.fredhopper.lang.query.ViewType;
import com.fredhopper.lang.query.location.Location;
import com.fredhopper.lang.query.location.criteria.*;
import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.FacetParameter.ParameterType;
import com.sdl.ecommerce.api.QueryResult;
import com.sdl.ecommerce.fredhopper.model.FredhopperCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Fredhopper Client.
 * Supports FAS v7.5.x
 *
 * @author nic
 */
@Service
public class FredhopperClient implements FredhopperLinkManager {

    private static final Logger LOG = LoggerFactory.getLogger(FredhopperClient.class);

    static final String WS_PATH = "/fredhopper-ws/services/FASWebService";
    static final String WSDL_PATH = "/fredhopper-ws/services/FASWebService?wsdl";

    @org.springframework.beans.factory.annotation.Value("${fredhopper.queryserver.url}")
    private String fredhopperBaseUrl = "http://localhost:8180";

    @org.springframework.beans.factory.annotation.Value("${fredhopper.access.username}")
    private String username = null;

    @org.springframework.beans.factory.annotation.Value("${fredhopper.access.password}")
    private String password = null;

    @org.springframework.beans.factory.annotation.Value("${fredhopper.imageurl.mappings}")
    private String imageUrlMappingsConfig = null;
    private Map<String,String> imageUrlMappings = new HashMap<>();

    private FASWebService fasService;

    @PostConstruct
    public void initialize() throws Exception {
        URL wsdlURL = new URL(this.fredhopperBaseUrl + WSDL_PATH);

        if ( username != null && !username.isEmpty() ) {

            // TODO: Is this bullet-proof?? Will that impact other basic authentication connections???
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });
        }
        FASWebServiceService fasServiceService = new FASWebServiceService(wsdlURL);

        this.fasService = fasServiceService.getFASWebService();
        ((BindingProvider) this.fasService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, fredhopperBaseUrl + WS_PATH);

        if ( imageUrlMappingsConfig != null ) {
            StringTokenizer tokenizer = new StringTokenizer(imageUrlMappingsConfig, "=,");
            while ( tokenizer.hasMoreTokens() ) {
                String imageUrlPrefix = tokenizer.nextToken();
                String imageUrlPrefixReplace = tokenizer.nextToken();
                imageUrlMappings.put(imageUrlPrefix, imageUrlPrefixReplace);
            }
        }
    }

    public ProductDetailResult getDetail(String productId, String universe, String locale) {
        Query query = this.buildQuery(universe, locale);
        query.addSecondId(productId);
        query.setView(ViewType.DETAIL);
        return new FredhopperDetailResult(this.doQuery(query), this);
    }


    Page doQuery(Query query) {
        return this.fasService.getAll(query.toQueryString());
    }


    /*
    public Page queryByCategory(Category category, ViewType pageType) {
        if ( pageType != null ) {
            QueryConfiguration queryConfig = new QueryConfiguration();
            queryConfig.setPageType(pageType);
            return this.queryByCategory(category, null, 0, queryConfig);
        }
        return this.queryByCategory(category);
    }
    */


    public QueryResult query(com.sdl.ecommerce.api.Query eCommerceQuery, String universe, String locale) {

        Query query = this.buildQuery(universe, locale);
        if ( eCommerceQuery.getViewType() != null ) {
            query.setView(this.convertViewType(eCommerceQuery.getViewType()));
        }
        if ( eCommerceQuery.getSearchPhrase() != null ) {
            query.setSearchPhrase(eCommerceQuery.getSearchPhrase());
            query.setView(ViewType.SEARCH);
        }
        this.buildCategoryQuery(query, eCommerceQuery.getCategory());
        this.buildFacetQuery(query, eCommerceQuery.getFacets());

        if ( eCommerceQuery.getStartIndex()  > 0 ) {
            query.setListStartIndex(eCommerceQuery.getStartIndex());
        }
        this.applyQueryConfiguration(query, (FredhopperQuery) eCommerceQuery);
        return new FredhopperQueryResult(this.doQuery(query), eCommerceQuery, this);
    }

    // TODO: Do a chain interface here instead

    public Query buildQuery(String universe, String locale) {
        Query query = new Query();
        //Location location = new Location(DEFAULT_LOCATION);
        Location location = new Location(universe, locale);
        query.setLocation(location);
        return query;
    }

    public void buildCategoryQuery(Query query, Category category) {
        List<String> categoryIds = new ArrayList<>();
        while ( category != null ) {
            categoryIds.add(0, category.getId());
            category = category.getParent();
        }
        for ( String categoryId: categoryIds ) {
            query.getLocation().addCriterion(new CategoryCriterion("categories", categoryId));
        }
    }

    public void buildFacetQuery(Query query, List<FacetParameter> facets) {
        if ( facets != null ) {
            for (FacetParameter facet : facets) {
                query.getLocation().addCriterion(toCriterion(facet));
            }
        }
    }

    public Criterion toCriterion(FacetParameter facet) {
        Criterion criterion = null;
        if (facet.getType() ==ParameterType.MULTISELECT) {
            com.fredhopper.lang.query.location.criteria.ValueSet valueSet = new com.fredhopper.lang.query.location.criteria.ValueSet(com.fredhopper.lang.query.location.criteria.ValueSet.AggregationType.OR);
            for (String value : facet.getValues()) {
                valueSet.add(value);
            }
            criterion = new MultiValuedCriterion(facet.getName(), valueSet, null, false);
        }
        else if (facet.getType() == ParameterType.RANGE) {
            criterion = new SingleValuedCriterion(facet.getName(), facet.getValues().get(0), facet.getValues().get(1), false);
        }
        else if ( facet.getType() == ParameterType.LESS_THAN ) {
            criterion = new SingleValuedCriterion(facet.getName(), null, facet.getValues().get(0), false);
        }
        else if ( facet.getType() == ParameterType.GREATER_THAN ) {
            criterion = new SingleValuedCriterion(facet.getName(), facet.getValues().get(0), null, false);
        }
        else if ( facet.getType() == ParameterType.SINGLEVALUE) {
            criterion = new SingleValuedCriterion(facet.getName(), facet.getValues().get(0));
        }
        return criterion;
    }

    public FacetParameter fromCriterion(Criterion criterion) {
        String name = criterion.getAttributeName();
        String value = null;
        if ( criterion instanceof MultiValuedCriterion ) {
            MultiValuedCriterion mvCriterion = (MultiValuedCriterion) criterion;
            com.fredhopper.lang.query.location.criteria.ValueSet valueSet = mvCriterion.getLessThan();
            if ( valueSet == null ) {
                valueSet = mvCriterion.getGreaterThan();
            }
            for ( String val : valueSet.values() ) {
                if ( value == null ) {
                    value = val;
                }
                else {
                    value += "|" + val;
                }
            }
        }
        else  if ( criterion instanceof SingleValuedCriterion ) {
            SingleValuedCriterion svCriterion = (SingleValuedCriterion) criterion;
            if ( ! svCriterion.isEqual() ) { // range
                if ( svCriterion.getLessThan() == null ) {
                    value = ">" + svCriterion.getGreaterThan();
                }
                else if ( svCriterion.getGreaterThan() == null ) {
                    value = "<" + svCriterion.getLessThan();
                }
                else {
                    value = svCriterion.getLessThan() + "-" + svCriterion.getGreaterThan();
                }
            }
            else {
                value = svCriterion.getLessThan();
            }
        }
        return new FacetParameter(name, value);
    }

    private ViewType convertViewType(com.sdl.ecommerce.api.ViewType genericViewType) {
        ViewType viewType;
        switch (genericViewType) {
            case HOME:
                viewType = ViewType.HOME;
                break;
            case SUMMARY:
                viewType = ViewType.SUMMARY;
                break;
            case SEARCH:
                viewType = ViewType.SEARCH;
                break;
            case DETAIL:
                viewType = ViewType.DETAIL;
                break;
            case COMPARE:
                viewType = ViewType.COMPARE;
                break;
            case FLYOUT:
                viewType = ViewType.HOME;
                break;
            case LISTER:
            default:
                viewType =  ViewType.LISTER;
                break;
        }
        return viewType;
    }

    private void applyQueryConfiguration(Query query, FredhopperQuery queryConfiguration) {
        if ( queryConfiguration != null ) {
            if ( queryConfiguration.getViewSize() > 0 ) {
                query.setListViewSize(queryConfiguration.getViewSize());
            }
            if ( queryConfiguration.isDisableRedirect() != null ) {
                query.setDisableRedirect(queryConfiguration.isDisableRedirect());
            }
            if ( queryConfiguration.isForcedNaturalSorting() != null && queryConfiguration.isForcedNaturalSorting() == true ) {
                query.setForcedNaturalSorting();
            }
            if ( queryConfiguration.getFacetLimits() != null ) {
                for ( String facet : queryConfiguration.getFacetLimits().keySet() ) {
                    query.setFacetLimit(facet, queryConfiguration.getFacetLimits().get(facet));
                }
            }
            if ( queryConfiguration.getPageType() != null ) {
                query.setView(queryConfiguration.getPageType());
            }
        }
    }

    public Page queryCategory(Query baseQuery, FredhopperCategory category) {
        Location location = new Location(baseQuery.getLocation());
        location.addCriterion(new CategoryCriterion("categories", category.getId()));
        Query query = new Query();
        query.setLocation(location);
        return this.doQuery(query);
    }

    public List<Category> getCategories(Category parent, Universe universe) {
        List<Category> categories = new ArrayList<>();
        List facetmapArray = universe.getFacetmap();
        Facetmap facetmap = (Facetmap) facetmapArray.get(0);
        List<Filter> filters = facetmap.getFilter();
        for (Filter filter : filters) {
            if ( filter.getBasetype().value().equals("cat") ) {
                for (Filtersection section : filter.getFiltersection()) {
                    categories.add(new FredhopperCategory(parent, section));
                }
            }
        }
        return categories;
    }

    public Location getLocation(Category category, String searchPhrase, String universe, String locale) {
        List<String> categoryIds = null;
        if ( category != null ) {
            categoryIds = new ArrayList<>();
            while (category != null) {
                categoryIds.add(0, category.getId());
                category = category.getParent();
            }
        }
        Location location = new Location(universe, locale);
        if ( categoryIds != null ) {
            for (String categoryId : categoryIds) {
                location.addCriterion(new CategoryCriterion("categories", categoryId));
            }
        }
        if ( searchPhrase != null ) {
            location.addCriterion(new SearchCriterion(searchPhrase));
        }
        return location;
    }


    public Universe getUniverse(Page page) {
        for (Universe u : page.getUniverses().getUniverse()) {
            if (UniverseType.SELECTED.equals(u.getType())) {
                return u;
            }
        }
        return null;
    }

    /************ LINK MANAGER INTERFACE ***********************************/

    @Override
    public String convertToSEOLink(String location) {
        if ( location.contains("%") ) { // URL encoded location string
            try {
                location = URLDecoder.decode(location, "utf8");
            } catch ( UnsupportedEncodingException e  ) {}
        }
        return this.convertToSEOLink(new Location(location));
    }

    @Override
    public String convertToSEOLink(Location location) {

        StringBuilder seoLink = new StringBuilder();

        String leafCategoryId = null;
        List<FacetParameter> facets = null;
        for ( Criterion criterion : (List<Criterion>) location.getAllCriteria() ) {

            if ( criterion.getAttributeName().equals("categories") ) {
                // Category
                //
                leafCategoryId = ((MultiValuedCriterion) criterion).getLessThan().values()[0];
            }
            else {
                // Facet
                //
                if ( facets == null ) {
                    facets = new ArrayList<>();
                }
                facets.add(fromCriterion(criterion));
            }
        }

        // TODO: FIX THIS CODE BELOW !!!! THIS IS NEEDED FOR PROMOTIONS !!!!!!!
        /*
        if ( leafCategoryId != null ) {
            Category category = this.categoryManager.getCategoryById(leafCategoryId);
            seoLink.append(category.getCategoryLink("/c"));  // TODO: Totally encapsylate the '/c'
        }
        if ( facets != null ) {
            seoLink.append(Facet.getFacetLink(facets));
        }
        */

        return seoLink.toString();

    }

    @Override
    public String processImageUrl(String imageUrl) {

        for ( String imageUrlPrefix : this.imageUrlMappings.keySet() ) {
            if ( imageUrl.startsWith(imageUrlPrefix) ) {
                imageUrl = imageUrl.replace(imageUrlPrefix, imageUrlMappings.get(imageUrlPrefix));
                break;
            }
        }
        return imageUrl;
    }
}

