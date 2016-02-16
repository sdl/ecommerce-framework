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
@JsonPropertyOrder({ "format", "url" })
public class Image {

  @JsonProperty("format")
  private String format;
  @JsonProperty("url")
  private String url;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The format.
   */
  @JsonProperty("format")
  public String getFormat() {
    return format;
  }

  /**
   * 
   * @param format
   *          The format.
   */
  @JsonProperty("format")
  public void setFormat(String format) {
    this.format = format;
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