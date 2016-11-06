package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.hybris.api.model.FacetPair;
import com.sdl.ecommerce.hybris.api.model.FacetValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Hybris Facet
 *
 * @author nic
 */
public class HybrisFacet implements Facet {

    // TODO: Have some kind of default implementations of the different E-Com APIs???

    private String id;
    private com.sdl.ecommerce.hybris.api.model.FacetValue hybrisFacetValue;
    private boolean isCategory = false;
    private List<String> values = new ArrayList<>();

    public HybrisFacet(FacetValue hybrisFacetValue) {

        this.hybrisFacetValue = hybrisFacetValue;
        int numberOfQueryFacets = hybrisFacetValue.getQueryFacets().size();
        if ( numberOfQueryFacets > 0 ) {
            FacetPair facetPair = hybrisFacetValue.getQueryFacets().get(numberOfQueryFacets-1);
            this.id = facetPair.getId();
            this.getValues().add(facetPair.getValue());
            if ( this.id.equals("category") ) {
                isCategory = true;
            }
        }


        //facetValue.getName(), this.getFacetUrl(facetValue.getQueryFacets(), urlPrefix), facetValue.getCount(), facetValue.isSelected());


        // TODO: How to get the facet values here?
        //hybrisFacetValue.getValues().

        /*


        facetValue.getQueryFacets()

         for ( String facetId : values.keySet() ) {
                if (!facetUrl.equals("?")) {
                    facetUrl += "&";
                }
                facetUrl += facetId + "=" + values.get(facetId);
            }
         */
    }

    @Override
    public String getId() {
        // Is id really correct here???
       return this.id;
    }

    @Override
    public String getTitle() {
        return this.hybrisFacetValue.getName();
    }

    @Override
    public int getCount() {
        return this.hybrisFacetValue.getCount();
    }

    @Override
    public boolean isSelected() {
        return this.hybrisFacetValue.isSelected();
    }

    @Override
    public boolean isCategory() {
        return isCategory;
    }

    @Override
    public FacetType getType() {
        return FacetType.MULTISELECT;
    }

    @Override
    public String getValue() {
        if ( !this.values.isEmpty() ) {
            return values.get(0);
        }
        return null;
    }

    @Override
    public List<String> getValues() {
        return this.values;
    }
}
