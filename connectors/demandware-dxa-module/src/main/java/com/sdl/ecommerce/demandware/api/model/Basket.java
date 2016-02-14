package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Basket
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {

    private String currency;
    private Float product_sub_total;
    private Float product_total;
    private Float shipping_total;
    private Float tax_total;
    private Float order_total;
    private List<ProductItem> product_items;
    private String etag;

    public Float getOrder_total() {
        return order_total;
    }

    public void setOrder_total(Float order_total) {
        this.order_total = order_total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getProduct_sub_total() {
        return product_sub_total;
    }

    public void setProduct_sub_total(Float product_sub_total) {
        this.product_sub_total = product_sub_total;
    }

    public Float getProduct_total() {
        return product_total;
    }

    public void setProduct_total(Float product_total) {
        this.product_total = product_total;
    }

    public Float getShipping_total() {
        return shipping_total;
    }

    public void setShipping_total(Float shipping_total) {
        this.shipping_total = shipping_total;
    }

    public Float getTax_total() {
        return tax_total;
    }

    public void setTax_total(Float tax_total) {
        this.tax_total = tax_total;
    }

    public List<ProductItem> getProduct_items() {
        return product_items;
    }

    public void setProduct_items(List<ProductItem> product_items) {
        this.product_items = product_items;
    }

    public int getNoOfProducts() {
        return this.product_items == null ? 0 : this.product_items.size();
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
