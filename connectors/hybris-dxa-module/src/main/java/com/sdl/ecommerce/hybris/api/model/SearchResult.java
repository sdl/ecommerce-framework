package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

/**
 * SearchResult
 *
 * @author nic
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "sorts"})
@Generated("org.jsonschema2pojo")
public class SearchResult {

    @JsonProperty("products")
    private List<Product> products;

    @JsonProperty("facets")
    private List<Facet> facets;

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("breadcrumbs")
    private List<Breadcrumb> breadcrumbs;

    @JsonProperty("freeTextSearch")
    private String freeTextSearch;

    @JsonProperty("currentQuery")
    private Query currentQuery;

    @JsonProperty("spellingSuggestion")
    private SpellingSuggestion spellingSuggestion;

    public List<Product> getProducts() {
        return products;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public String getFreeTextSearch() {
        return freeTextSearch;
    }

    public Query getCurrentQuery() {
        return currentQuery;
    }

    public SpellingSuggestion getSpellingSuggestion() {
        return spellingSuggestion;
    }
}
