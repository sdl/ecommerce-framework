package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryFilterAttribute;
import com.sdl.ecommerce.api.ViewType;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.List;

/**
 * OData Product Query
 *
 * @author nic
 */


// TODO: IS THIS ONE USED????
public class ODataProductQuery {

    private Category category;

    @EdmProperty
    private String searchPhrase;


    private List<FacetParameter> facets;

    @EdmProperty
    private int startIndex;

    @EdmProperty
    private int viewSize;


    private List<QueryFilterAttribute> filterAttributes;

    @EdmProperty
    private String viewType;

    // Query must be a function as it returns a query result
    // where the query is a complex type passed as a input parameter??
    // But then we need to post the actual data....

    // NO -> We want to use the $filter approach

    // /ecommerce.svc/ProductQuery?#filter=searchPhrase eq 'something' and startIndex eq 20 and category = '' and facets

    // Probably function is better:
    // - ecommerce.svc/ListProducts(category,
    // - ecommerce.svc/ProductSearch(searchPhrase, category, facets,

}
