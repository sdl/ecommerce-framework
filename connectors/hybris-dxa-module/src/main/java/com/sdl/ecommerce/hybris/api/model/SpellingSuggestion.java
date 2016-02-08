package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

/**
 * SpellingSuggestion
 *
 * @author nic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class SpellingSuggestion {

    @JsonProperty("query")
    private String query;

    @JsonProperty("suggestion")
    private String suggestion;

    public String getQuery() {
        return query;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
