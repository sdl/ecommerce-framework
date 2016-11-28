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
@JsonPropertyOrder({ "code", "codeLowerCase" })
public class StockLevelStatus {

  @JsonProperty("code")
  private String code;
  @JsonProperty("codeLowerCase")
  private String codeLowerCase;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
   * @return The codeLowerCase.
   */
  @JsonProperty("codeLowerCase")
  public String getCodeLowerCase() {
    return codeLowerCase;
  }

  /**
   * 
   * @param codeLowerCase
   *          The codeLowerCase.
   */
  @JsonProperty("codeLowerCase")
  public void setCodeLowerCase(String codeLowerCase) {
    this.codeLowerCase = codeLowerCase;
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