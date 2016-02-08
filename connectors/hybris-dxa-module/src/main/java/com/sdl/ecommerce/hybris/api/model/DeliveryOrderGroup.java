package com.sdl.ecommerce.hybris.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "entries", "totalPriceWithTax" })
public class DeliveryOrderGroup {

	@JsonProperty("entries")
	private List<Entry> entries = new ArrayList<Entry>();
	@JsonProperty("totalPriceWithTax")
	private TotalPriceWithTax totalPriceWithTax;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The entries
	 */
	@JsonProperty("entries")
	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * 
	 * @param entries
	 *            The entries
	 */
	@JsonProperty("entries")
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	/**
	 * 
	 * @return The totalPriceWithTax
	 */
	@JsonProperty("totalPriceWithTax")
	public TotalPriceWithTax getTotalPriceWithTax() {
		return totalPriceWithTax;
	}

	/**
	 * 
	 * @param totalPriceWithTax
	 *            The totalPriceWithTax
	 */
	@JsonProperty("totalPriceWithTax")
	public void setTotalPriceWithTax(TotalPriceWithTax totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
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