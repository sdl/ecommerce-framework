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
@JsonPropertyOrder({ "currencyIso", "priceType", "value", "formattedValue" })
public class TotalTax {

  @JsonProperty("currencyIso")
  private String currencyIso;
  @JsonProperty("priceType")
  private String priceType;
  @JsonProperty("value")
  private String value;
  @JsonProperty("formattedValue")
  private String formattedValue;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The currencyIso.
   */
  @JsonProperty("currencyIso")
  public String getCurrencyIso() {
    return currencyIso;
  }

  /**
   * 
   * @param currencyIso
   *          The currencyIso.
   */
  @JsonProperty("currencyIso")
  public void setCurrencyIso(String currencyIso) {
    this.currencyIso = currencyIso;
  }

  /**
   * 
   * @return The priceType.
   */
  @JsonProperty("priceType")
  public String getPriceType() {
    return priceType;
  }

  /**
   * 
   * @param priceType
   *          The priceType.
   */
  @JsonProperty("priceType")
  public void setPriceType(String priceType) {
    this.priceType = priceType;
  }

  /**
   * 
   * @return The value.
   */
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  /**
   * 
   * @param value
   *          The value.
   */
  @JsonProperty("value")
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 
   * @return The formattedValue.
   */
  @JsonProperty("formattedValue")
  public String getFormattedValue() {
    return formattedValue;
  }

  /**
   * 
   * @param formattedValue
   *          The formattedValue.
   */
  @JsonProperty("formattedValue")
  public void setFormattedValue(String formattedValue) {
    this.formattedValue = formattedValue;
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