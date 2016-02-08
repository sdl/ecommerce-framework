package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "productReferences", "purchasable", "genders", "stock",
    "description", "name", "baseOptions", "baseProduct", "reviews", "code",
    "url", "price", "numberOfReviews", "images", "categories",
    "potentialPromotions" })
public class ProductData {

  @JsonProperty("productReferences")
  private List<Object> productReferences = new ArrayList<Object>();
  @JsonProperty("purchasable")
  private Boolean purchasable;
  @JsonProperty("genders")
  private List<Gender> genders = new ArrayList<Gender>();
  @JsonProperty("stock")
  private Stock stock;
  @JsonProperty("description")
  private String description;
  @JsonProperty("name")
  private String name;
  @JsonProperty("baseOptions")
  private List<BaseOption> baseOptions = new ArrayList<BaseOption>();
  @JsonProperty("baseProduct")
  private String baseProduct;
  @JsonProperty("reviews")
  private List<Object> reviews = new ArrayList<Object>();
  @JsonProperty("code")
  private String code;
  @JsonProperty("url")
  private String url;
  @JsonProperty("price")
  private Price price;
  @JsonProperty("numberOfReviews")
  private Integer numberOfReviews;
  @JsonProperty("images")
  private List<Image> images = new ArrayList<Image>();
  @JsonProperty("categories")
  private List<Category> categories = new ArrayList<Category>();
  @JsonProperty("potentialPromotions")
  private List<Object> potentialPromotions = new ArrayList<Object>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The productReferences.
   */
  @JsonProperty("productReferences")
  public List<Object> getProductReferences() {
    return productReferences;
  }

  /**
   * 
   * @param productReferences
   *          The productReferences.
   */
  @JsonProperty("productReferences")
  public void setProductReferences(List<Object> productReferences) {
    this.productReferences = productReferences;
  }

  /**
   * 
   * @return The purchasable.
   */
  @JsonProperty("purchasable")
  public Boolean getPurchasable() {
    return purchasable;
  }

  /**
   * 
   * @param purchasable
   *          The purchasable.
   */
  @JsonProperty("purchasable")
  public void setPurchasable(Boolean purchasable) {
    this.purchasable = purchasable;
  }

  /**
   * 
   * @return The genders.
   */
  @JsonProperty("genders")
  public List<Gender> getGenders() {
    return genders;
  }

  /**
   * 
   * @param genders
   *          The genders.
   */
  @JsonProperty("genders")
  public void setGenders(List<Gender> genders) {
    this.genders = genders;
  }

  /**
   * 
   * @return The stock.
   */
  @JsonProperty("stock")
  public Stock getStock() {
    return stock;
  }

  /**
   * 
   * @param stock
   *          The stock.
   */
  @JsonProperty("stock")
  public void setStock(Stock stock) {
    this.stock = stock;
  }

  /**
   * 
   * @return The description.
   */
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  /**
   * 
   * @param description
   *          The description.
   */
  @JsonProperty("description")
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 
   * @return The name.
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  /**
   * 
   * @param name
   *          The name.
   */
  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 
   * @return The baseOptions.
   */
  @JsonProperty("baseOptions")
  public List<BaseOption> getBaseOptions() {
    return baseOptions;
  }

  /**
   * 
   * @param baseOptions
   *          The baseOptions.
   */
  @JsonProperty("baseOptions")
  public void setBaseOptions(List<BaseOption> baseOptions) {
    this.baseOptions = baseOptions;
  }

  /**
   * 
   * @return The baseProduct.
   */
  @JsonProperty("baseProduct")
  public String getBaseProduct() {
    return baseProduct;
  }

  /**
   * 
   * @param baseProduct
   *          The baseProduct.
   */
  @JsonProperty("baseProduct")
  public void setBaseProduct(String baseProduct) {
    this.baseProduct = baseProduct;
  }

  /**
   * 
   * @return The reviews.
   */
  @JsonProperty("reviews")
  public List<Object> getReviews() {
    return reviews;
  }

  /**
   * 
   * @param reviews
   *          The reviews.
   */
  @JsonProperty("reviews")
  public void setReviews(List<Object> reviews) {
    this.reviews = reviews;
  }

  /**
   * 
   * @return The code.
   */
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  /**
   * 
   * @param code
   *          The code.
   */
  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 
   * @return The url.
   */
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }

  /**
   * 
   * @param url
   *          The url.
   */
  @JsonProperty("url")
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 
   * @return The price.
   */
  @JsonProperty("price")
  public Price getPrice() {
    return price;
  }

  /**
   * 
   * @param price
   *          The price.
   */
  @JsonProperty("price")
  public void setPrice(Price price) {
    this.price = price;
  }

  /**
   * 
   * @return The numberOfReviews.
   */
  @JsonProperty("numberOfReviews")
  public Integer getNumberOfReviews() {
    return numberOfReviews;
  }

  /**
   * 
   * @param numberOfReviews
   *          The numberOfReviews.
   */
  @JsonProperty("numberOfReviews")
  public void setNumberOfReviews(Integer numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
  }

  /**
   * 
   * @return The images.
   */
  @JsonProperty("images")
  public List<Image> getImages() {
    return images;
  }

  /**
   * 
   * @param images
   *          The images.
   */
  @JsonProperty("images")
  public void setImages(List<Image> images) {
    this.images = images;
  }

  /**
   * 
   * @return The categories.
   */
  @JsonProperty("categories")
  public List<Category> getCategories() {
    return categories;
  }

  /**
   * 
   * @param categories
   *          The categories.
   */
  @JsonProperty("categories")
  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  /**
   * 
   * @return The potentialPromotions.
   */
  @JsonProperty("potentialPromotions")
  public List<Object> getPotentialPromotions() {
    return potentialPromotions;
  }

  /**
   * 
   * @param potentialPromotions
   *          The potentialPromotions.
   */
  @JsonProperty("potentialPromotions")
  public void setPotentialPromotions(List<Object> potentialPromotions) {
    this.potentialPromotions = potentialPromotions;
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
