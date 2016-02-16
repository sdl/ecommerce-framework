package com.sdl.ecommerce.hybris.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "purchasable",
        "stock",
        "name",
        "description",
        "baseOptions",
        "availableForPickup",
        "code",
        "url",
        "manufacturer",
        "price",
        "images",
        "categories"
})
public class Product {

    @JsonProperty("purchasable")
    private Boolean purchasable;

    @JsonProperty("stock")
    private Stock stock;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("baseOptions")
    private List<Object> baseOptions = new ArrayList<Object>();

    @JsonProperty("availableForPickup")
    private Boolean availableForPickup;

    @JsonProperty("code")
    private String code;

    @JsonProperty("url")
    private String url;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("numberOfReviews")
    private Integer numberOfReviews;

    @JsonProperty("images")
    private List<Image> images = new ArrayList<Image>();

    @JsonProperty("categories")
    private List<Category> categories = new ArrayList<Category>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getPurchasable() {
        return purchasable;
    }

    public Stock getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getBaseOptions() {
        return baseOptions;
    }

    public Boolean getAvailableForPickup() {
        return availableForPickup;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public Price getPrice() {
        return price;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Category> getCategories() {
        return categories;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}