package com.sdl.ecommerce.hybris.api.model;

import java.util.HashMap;
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
"product",
"entryNumber",
"updateable",
"quantity",
"basePrice",
"totalPrice"
})
public class Entry {

@JsonProperty("product")
private Product product;
@JsonProperty("entryNumber")
private Integer entryNumber;
@JsonProperty("updateable")
private Boolean updateable;
@JsonProperty("quantity")
private Integer quantity;
@JsonProperty("basePrice")
private Price basePrice;
@JsonProperty("totalPrice")
private Price totalPrice;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The product
*/
@JsonProperty("product")
public Product getProduct() {
return product;
}

/**
* 
* @param product
* The product
*/
@JsonProperty("product")
public void setProduct(Product product) {
this.product = product;
}

/**
* 
* @return
* The entryNumber
*/
@JsonProperty("entryNumber")
public Integer getEntryNumber() {
return entryNumber;
}

/**
* 
* @param entryNumber
* The entryNumber
*/
@JsonProperty("entryNumber")
public void setEntryNumber(Integer entryNumber) {
this.entryNumber = entryNumber;
}

/**
* 
* @return
* The updateable
*/
@JsonProperty("updateable")
public Boolean getUpdateable() {
return updateable;
}

/**
* 
* @param updateable
* The updateable
*/
@JsonProperty("updateable")
public void setUpdateable(Boolean updateable) {
this.updateable = updateable;
}

/**
* 
* @return
* The quantity
*/
@JsonProperty("quantity")
public Integer getQuantity() {
return quantity;
}

/**
* 
* @param quantity
* The quantity
*/
@JsonProperty("quantity")
public void setQuantity(Integer quantity) {
this.quantity = quantity;
}

/**
* 
* @return
* The basePrice
*/
@JsonProperty("basePrice")
public Price getBasePrice() {
return basePrice;
}

/**
* 
* @param basePrice
* The basePrice
*/
@JsonProperty("basePrice")
public void setBasePrice(Price basePrice) {
this.basePrice = basePrice;
}

/**
* 
* @return
* The totalPrice
*/
@JsonProperty("totalPrice")
public Price getTotalPrice() {
return totalPrice;
}

/**
* 
* @param totalPrice
* The totalPrice
*/
@JsonProperty("totalPrice")
public void setTotalPrice(Price totalPrice) {
this.totalPrice = totalPrice;
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