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
@JsonPropertyOrder({ "selected", "variantType" })
public class BaseOption {

  @JsonProperty("selected")
  private Selected selected;
  @JsonProperty("variantType")
  private String variantType;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The selected.
   */
  @JsonProperty("selected")
  public Selected getSelected() {
    return selected;
  }

  /**
   * 
   * @param selected
   *          The selected.
   */
  @JsonProperty("selected")
  public void setSelected(Selected selected) {
    this.selected = selected;
  }

  /**
   * 
   * @return The variantType.
   */
  @JsonProperty("variantType")
  public String getVariantType() {
    return variantType;
  }

  /**
   * 
   * @param variantType
   *          The variantType.
   */
  @JsonProperty("variantType")
  public void setVariantType(String variantType) {
    this.variantType = variantType;
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