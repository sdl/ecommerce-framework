package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.*;

/**
 * FacetValue
 *
 * @author nic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class FacetValue {

    @JsonProperty("name")
    private String name;

    @JsonProperty("count")
    private int count;

    @JsonProperty("selected")
    private boolean selected;

    @JsonProperty("query")
    private String query;

    public String getId() {
        if ( selected ) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer(query, ":");
        int tokens = tokenizer.countTokens();
        for ( int i=0; i < tokens-1; i++) {
            tokenizer.nextToken();
        }
        return tokenizer.nextToken();

    }
    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getQuery() {
        return query;
    }

    public List<FacetPair> getQueryFacets() {
        StringTokenizer tokenizer = new StringTokenizer(query, ":");
        if ( !query.startsWith(":") ) {
            tokenizer.nextToken(); // with search phrase
        }
        tokenizer.nextToken();
        List<FacetPair> facets = new ArrayList<>();
        while ( tokenizer.hasMoreTokens() ) {
            facets.add(new FacetPair(tokenizer.nextToken(), tokenizer.nextToken()));
        }
        return facets;
    }
}
