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
@JsonPropertyOrder({ "store", "appliedOrderPromotions", "pickupItemsQuantity",
    "net", "calculated", "appliedVouchers", "productDiscounts",
    "totalDiscounts", "subTotal", "orderDiscounts", "entries",
    "appliedProductPromotions", "totalPrice", "site", "code",
    "deliveryOrderGroups", "guid", "pickupOrderGroups", "totalItems",
    "totalPriceWithTax", "deliveryItemsQuantity", "totalTax",
    "potentialProductPromotions", "potentialOrderPromotions", "totalUnitCount" })
public class Cart {

  @JsonProperty("store")
  private String store;
  @JsonProperty("appliedOrderPromotions")
  private List<Object> appliedOrderPromotions = new ArrayList<Object>();
  @JsonProperty("pickupItemsQuantity")
  private Integer pickupItemsQuantity;
  @JsonProperty("net")
  private Boolean net;
  @JsonProperty("calculated")
  private Boolean calculated;
  @JsonProperty("appliedVouchers")
  private List<Object> appliedVouchers = new ArrayList<Object>();
  @JsonProperty("productDiscounts")
  private Price productDiscounts;
  @JsonProperty("totalDiscounts")
  private Price totalDiscounts;
  @JsonProperty("subTotal")
  private Price subTotal;
  @JsonProperty("orderDiscounts")
  private Price orderDiscounts;
  @JsonProperty("entries")
  private List<Entry> entries = new ArrayList<Entry>();
  @JsonProperty("appliedProductPromotions")
  private List<Object> appliedProductPromotions = new ArrayList<Object>();
  @JsonProperty("totalPrice")
  private Price totalPrice;
  @JsonProperty("site")
  private String site;
  @JsonProperty("code")
  private String code;
  @JsonProperty("deliveryOrderGroups")
  private List<Object> deliveryOrderGroups = new ArrayList<Object>();
  @JsonProperty("guid")
  private String guid;
  @JsonProperty("pickupOrderGroups")
  private List<Object> pickupOrderGroups = new ArrayList<Object>();
  @JsonProperty("totalItems")
  private Integer totalItems;
  @JsonProperty("totalPriceWithTax")
  private Price totalPriceWithTax;
  @JsonProperty("deliveryItemsQuantity")
  private Integer deliveryItemsQuantity;
  @JsonProperty("totalTax")
  private Price totalTax;
  @JsonProperty("potentialProductPromotions")
  private List<Object> potentialProductPromotions = new ArrayList<Object>();
  @JsonProperty("potentialOrderPromotions")
  private List<Object> potentialOrderPromotions = new ArrayList<Object>();
  @JsonProperty("totalUnitCount")
  private Integer totalUnitCount;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * 
   * @return The store.
   */
  @JsonProperty("store")
  public String getStore() {
    return store;
  }

  /**
   * 
   * @param store
   *          The store.
   */
  @JsonProperty("store")
  public void setStore(String store) {
    this.store = store;
  }

  /**
   * 
   * @return The appliedOrderPromotions.
   */
  @JsonProperty("appliedOrderPromotions")
  public List<Object> getAppliedOrderPromotions() {
    return appliedOrderPromotions;
  }

  /**
   * 
   * @param appliedOrderPromotions
   *          The appliedOrderPromotions.
   */
  @JsonProperty("appliedOrderPromotions")
  public void setAppliedOrderPromotions(List<Object> appliedOrderPromotions) {
    this.appliedOrderPromotions = appliedOrderPromotions;
  }

  /**
   * 
   * @return The pickupItemsQuantity.
   */
  @JsonProperty("pickupItemsQuantity")
  public Integer getPickupItemsQuantity() {
    return pickupItemsQuantity;
  }

  /**
   * 
   * @param pickupItemsQuantity
   *          The pickupItemsQuantity.
   */
  @JsonProperty("pickupItemsQuantity")
  public void setPickupItemsQuantity(Integer pickupItemsQuantity) {
    this.pickupItemsQuantity = pickupItemsQuantity;
  }

  /**
   * 
   * @return The net.
   */
  @JsonProperty("net")
  public Boolean getNet() {
    return net;
  }

  /**
   * 
   * @param net
   *          The net.
   */
  @JsonProperty("net")
  public void setNet(Boolean net) {
    this.net = net;
  }

  /**
   * 
   * @return The calculated.
   */
  @JsonProperty("calculated")
  public Boolean getCalculated() {
    return calculated;
  }

  /**
   * 
   * @param calculated
   *          The calculated.
   */
  @JsonProperty("calculated")
  public void setCalculated(Boolean calculated) {
    this.calculated = calculated;
  }

  /**
   * 
   * @return The appliedVouchers.
   */
  @JsonProperty("appliedVouchers")
  public List<Object> getAppliedVouchers() {
    return appliedVouchers;
  }

  /**
   * 
   * @param appliedVouchers
   *          The appliedVouchers.
   */
  @JsonProperty("appliedVouchers")
  public void setAppliedVouchers(List<Object> appliedVouchers) {
    this.appliedVouchers = appliedVouchers;
  }

  /**
   * 
   * @return The productDiscounts.
   */
  @JsonProperty("productDiscounts")
  public Price getProductDiscounts() {
    return productDiscounts;
  }

  /**
   * 
   * @param productDiscounts
   *          The productDiscounts.
   */
  @JsonProperty("productDiscounts")
  public void setProductDiscounts(Price productDiscounts) {
    this.productDiscounts = productDiscounts;
  }

  /**
   * 
   * @return The totalDiscounts.
   */
  @JsonProperty("totalDiscounts")
  public Price getTotalDiscounts() {
    return totalDiscounts;
  }

  /**
   * 
   * @param totalDiscounts
   *          The totalDiscounts.
   */
  @JsonProperty("totalDiscounts")
  public void setTotalDiscounts(Price totalDiscounts) {
    this.totalDiscounts = totalDiscounts;
  }

  /**
   * 
   * @return The subTotal.
   */
  @JsonProperty("subTotal")
  public Price getSubTotal() {
    return subTotal;
  }

  /**
   * 
   * @param subTotal
   *          The subTotal.
   */
  @JsonProperty("subTotal")
  public void setSubTotal(Price subTotal) {
    this.subTotal = subTotal;
  }

  /**
   * 
   * @return The orderDiscounts.
   */
  @JsonProperty("orderDiscounts")
  public Price getOrderDiscounts() {
    return orderDiscounts;
  }

  /**
   * 
   * @param orderDiscounts
   *          The orderDiscounts.
   */
  @JsonProperty("orderDiscounts")
  public void setOrderDiscounts(Price orderDiscounts) {
    this.orderDiscounts = orderDiscounts;
  }

  /**
   * 
   * @return The entries.
   */
  @JsonProperty("entries")
  public List<Entry> getEntries() {
    return entries;
  }

  /**
   * 
   * @param entries
   *          The entries.
   */
  @JsonProperty("entries")
  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  /**
   * 
   * @return The appliedProductPromotions.
   */
  @JsonProperty("appliedProductPromotions")
  public List<Object> getAppliedProductPromotions() {
    return appliedProductPromotions;
  }

  /**
   * 
   * @param appliedProductPromotions
   *          The appliedProductPromotions.
   */
  @JsonProperty("appliedProductPromotions")
  public void setAppliedProductPromotions(List<Object> appliedProductPromotions) {
    this.appliedProductPromotions = appliedProductPromotions;
  }

  /**
   * 
   * @return The totalPrice.
   */
  @JsonProperty("totalPrice")
  public Price getTotalPrice() {
    return totalPrice;
  }

  /**
   * 
   * @param totalPrice
   *          The totalPrice.
   */
  @JsonProperty("totalPrice")
  public void setTotalPrice(Price totalPrice) {
    this.totalPrice = totalPrice;
  }

  /**
   * 
   * @return The site.
   */
  @JsonProperty("site")
  public String getSite() {
    return site;
  }

  /**
   * 
   * @param site
   *          The site.
   */
  @JsonProperty("site")
  public void setSite(String site) {
    this.site = site;
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
   * @return The deliveryOrderGroups.
   */
  @JsonProperty("deliveryOrderGroups")
  public List<Object> getDeliveryOrderGroups() {
    return deliveryOrderGroups;
  }

  /**
   * 
   * @param deliveryOrderGroups
   *          The deliveryOrderGroups.
   */
  @JsonProperty("deliveryOrderGroups")
  public void setDeliveryOrderGroups(List<Object> deliveryOrderGroups) {
    this.deliveryOrderGroups = deliveryOrderGroups;
  }

  /**
   * 
   * @return The guid.
   */
  @JsonProperty("guid")
  public String getGuid() {
    return guid;
  }

  /**
   * 
   * @param guid
   *          The guid.
   */
  @JsonProperty("guid")
  public void setGuid(String guid) {
    this.guid = guid;
  }

  /**
   * 
   * @return The pickupOrderGroups.
   */
  @JsonProperty("pickupOrderGroups")
  public List<Object> getPickupOrderGroups() {
    return pickupOrderGroups;
  }

  /**
   * 
   * @param pickupOrderGroups
   *          The pickupOrderGroups.
   */
  @JsonProperty("pickupOrderGroups")
  public void setPickupOrderGroups(List<Object> pickupOrderGroups) {
    this.pickupOrderGroups = pickupOrderGroups;
  }

  /**
   * 
   * @return The totalItems.
   */
  @JsonProperty("totalItems")
  public Integer getTotalItems() {
    return totalItems;
  }

  /**
   * 
   * @param totalItems
   *          The totalItems.
   */
  @JsonProperty("totalItems")
  public void setTotalItems(Integer totalItems) {
    this.totalItems = totalItems;
  }

  /**
   * 
   * @return The totalPriceWithTax.
   */
  @JsonProperty("totalPriceWithTax")
  public Price getTotalPriceWithTax() {
    return totalPriceWithTax;
  }

  /**
   * 
   * @param totalPriceWithTax
   *          The totalPriceWithTax.
   */
  @JsonProperty("totalPriceWithTax")
  public void setTotalPriceWithTax(Price totalPriceWithTax) {
    this.totalPriceWithTax = totalPriceWithTax;
  }

  /**
   * 
   * @return The deliveryItemsQuantity.
   */
  @JsonProperty("deliveryItemsQuantity")
  public Integer getDeliveryItemsQuantity() {
    return deliveryItemsQuantity;
  }

  /**
   * 
   * @param deliveryItemsQuantity
   *          The deliveryItemsQuantity.
   */
  @JsonProperty("deliveryItemsQuantity")
  public void setDeliveryItemsQuantity(Integer deliveryItemsQuantity) {
    this.deliveryItemsQuantity = deliveryItemsQuantity;
  }

  /**
   * 
   * @return The totalTax.
   */
  @JsonProperty("totalTax")
  public Price getTotalTax() {
    return totalTax;
  }

  /**
   * 
   * @param totalTax
   *          The totalTax.
   */
  @JsonProperty("totalTax")
  public void setTotalTax(Price totalTax) {
    this.totalTax = totalTax;
  }

  /**
   * 
   * @return The potentialProductPromotions.
   */
  @JsonProperty("potentialProductPromotions")
  public List<Object> getPotentialProductPromotions() {
    return potentialProductPromotions;
  }

  /**
   * 
   * @param potentialProductPromotions
   *          The potentialProductPromotions.
   */
  @JsonProperty("potentialProductPromotions")
  public void setPotentialProductPromotions(
      List<Object> potentialProductPromotions) {
    this.potentialProductPromotions = potentialProductPromotions;
  }

  /**
   * 
   * @return The potentialOrderPromotions.
   */
  @JsonProperty("potentialOrderPromotions")
  public List<Object> getPotentialOrderPromotions() {
    return potentialOrderPromotions;
  }

  /**
   * 
   * @param potentialOrderPromotions
   *          The potentialOrderPromotions.
   */
  @JsonProperty("potentialOrderPromotions")
  public void setPotentialOrderPromotions(List<Object> potentialOrderPromotions) {
    this.potentialOrderPromotions = potentialOrderPromotions;
  }

  /**
   * 
   * @return The totalUnitCount.
   */
  @JsonProperty("totalUnitCount")
  public Integer getTotalUnitCount() {
    return totalUnitCount;
  }

  /**
   * 
   * @param totalUnitCount
   *          The totalUnitCount.
   */
  @JsonProperty("totalUnitCount")
  public void setTotalUnitCount(Integer totalUnitCount) {
    this.totalUnitCount = totalUnitCount;
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