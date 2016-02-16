package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pagination
 *
 * @author nic
 */
public class Pagination {

    @JsonProperty("sort")
    private String sort;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("currentPage")
    private int currentPage;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("totalPages")
    private int totalPages;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSort() {
        return sort;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
