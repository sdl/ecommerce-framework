package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

/**
 * Breadcrumb
 *
 * @author nic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Breadcrumb {

    @JsonProperty("facetName")
    private String facetName;

    @JsonProperty("facetValueName")
    private String facetValueName;

    @JsonProperty("facetCode")
    private String facetCode;

    @JsonProperty("facetValueCode")
    private String facetValueCode;

    @JsonProperty("truncateQuery")
    private Query truncateQuery;

    @JsonProperty("removeQuery")
    private Query removeQuery;

    public String getFacetCode() {
        return facetCode;
    }

    public String getFacetName() {
        return facetName;
    }

    public String getFacetValueCode() {
        return facetValueCode;
    }

    public String getFacetValueName() {
        return facetValueName;
    }

    public Query getRemoveQuery() {
        return removeQuery;
    }

    public Query getTruncateQuery() {
        return truncateQuery;
    }
}
