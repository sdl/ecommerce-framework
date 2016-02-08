package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "stockLevelStatus", "stockLevel" })
public class Stock {

  @JsonProperty("stockLevelStatus")
  private StockLevelStatus stockLevelStatus;
  @JsonProperty("stockLevel")
  private Integer stockLevel;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The stockLevelStatus.
   */
  @JsonProperty("stockLevelStatus")
  public StockLevelStatus getStockLevelStatus() {
    return stockLevelStatus;
  }

  /**
   * 
   * @param stockLevelStatus
   *          The stockLevelStatus.
   */
  @JsonProperty("stockLevelStatus")
  public void setStockLevelStatus(StockLevelStatus stockLevelStatus) {
    this.stockLevelStatus = stockLevelStatus;
  }

  /**
   * 
   * @return The stockLevel.
   */
  @JsonProperty("stockLevel")
  public Integer getStockLevel() {
    return stockLevel;
  }

  /**
   * 
   * @param stockLevel
   *          The stockLevel.
   */
  @JsonProperty("stockLevel")
  public void setStockLevel(Integer stockLevel) {
    this.stockLevel = stockLevel;
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