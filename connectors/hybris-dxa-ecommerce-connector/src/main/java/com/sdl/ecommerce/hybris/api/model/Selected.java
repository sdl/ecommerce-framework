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
@JsonPropertyOrder({ "variantOptionQualifiers", "stock", "priceData", "code",
    "url" })
public class Selected {

  @JsonProperty("variantOptionQualifiers")
  private List<VariantOptionQualifier> variantOptionQualifiers = 
      new ArrayList<VariantOptionQualifier>();
  @JsonProperty("stock")
  private Stock stock;
  @JsonProperty("priceData")
  private PriceData priceData;
  @JsonProperty("code")
  private String code;
  @JsonProperty("url")
  private String url;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The variantOptionQualifiers.
   */
  @JsonProperty("variantOptionQualifiers")
  public List<VariantOptionQualifier> getVariantOptionQualifiers() {
    return variantOptionQualifiers;
  }

  /**
   * 
   * @param variantOptionQualifiers
   *          The variantOptionQualifiers.
   */
  @JsonProperty("variantOptionQualifiers")
  public void setVariantOptionQualifiers(
      List<VariantOptionQualifier> variantOptionQualifiers) {
    this.variantOptionQualifiers = variantOptionQualifiers;
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
   * @return The priceData.
   */
  @JsonProperty("priceData")
  public PriceData getPriceData() {
    return priceData;
  }

  /**
   * 
   * @param priceData
   *          The priceData.
   */
  @JsonProperty("priceData")
  public void setPriceData(PriceData priceData) {
    this.priceData = priceData;
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

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
