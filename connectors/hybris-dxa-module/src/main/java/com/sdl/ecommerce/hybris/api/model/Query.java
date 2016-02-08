package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Query
 *
 * @author nic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Query {

    static public class QueryValue {
        @JsonProperty("value")
        String value;
    }

    @JsonProperty("query")
    private QueryValue query;

    @JsonProperty("url")
    private String url;

    public String getValue() {
        return query.value;
    }

    public String getUrl() {
        return url;
    }

    public List<FacetPair> getFacets() {
        StringTokenizer tokenizer = new StringTokenizer(query.value, ":");
        if ( !query.value.startsWith(":") ) {
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
