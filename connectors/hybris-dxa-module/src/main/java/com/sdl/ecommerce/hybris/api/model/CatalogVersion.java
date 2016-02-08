package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * CatalogVersion
 *
 * @author nic
 */
public class CatalogVersion {

    @JsonProperty("id")
    private String id;

    @JsonProperty("lastModified")
    private Date lastModified;

    @JsonProperty("url")
    private String url;

    @JsonProperty("categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public String getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getUrl() {
        return url;
    }
}
