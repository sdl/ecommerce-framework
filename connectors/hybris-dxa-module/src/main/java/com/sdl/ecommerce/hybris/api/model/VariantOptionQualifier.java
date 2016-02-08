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
@JsonPropertyOrder({ "name", "value", "image", "qualifier" })
public class VariantOptionQualifier {

  @JsonProperty("name")
  private String name;
  @JsonProperty("value")
  private String value;
  @JsonProperty("image")
  private Image image;
  @JsonProperty("qualifier")
  private String qualifier;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
   * @return The image.
   */
  @JsonProperty("image")
  public Image getImage() {
    return image;
  }

  /**
   * 
   * @param image
   *          The image.
   */
  @JsonProperty("image")
  public void setImage(Image image) {
    this.image = image;
  }

  /**
   * 
   * @return The qualifier.
   */
  @JsonProperty("qualifier")
  public String getQualifier() {
    return qualifier;
  }

  /**
   * 
   * @param qualifier
   *          The qualifier.
   */
  @JsonProperty("qualifier")
  public void setQualifier(String qualifier) {
    this.qualifier = qualifier;
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