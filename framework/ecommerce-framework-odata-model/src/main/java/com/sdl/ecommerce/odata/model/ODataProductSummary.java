package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.*;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.List;
import java.util.Map;

/**
 * OData Product Summary
 *
 * @author nic
 */
@EdmComplex(name="ProductSummary", namespace = "SDL.ECommerce")
public class ODataProductSummary implements Product {


    @EdmProperty
    private String id;

    @EdmProperty
    private String variantId;

    @EdmProperty
    private String name;

    @EdmProperty
    private ODataProductPrice price;

    @EdmProperty
    private String thumbnailUrl;


    public ODataProductSummary() {}
    public ODataProductSummary(Product product) {
        this.id = product.getId();
        this.variantId = product.getVariantId();
        this.name = product.getName();
        if ( product.getPrice() != null ) {
            this.price = new ODataProductPrice(product.getPrice());
        }
        this.thumbnailUrl = product.getThumbnailUrl();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getVariantId() { return this.variantId; }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProductPrice getPrice() {
        return this.price;
    }

    @Override
    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    /********* Data only available for product details ***********/

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getPrimaryImageUrl() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return null;
    }

    @Override
    public List<ProductVariant> getVariants() {
        return null;
    }

    @Override
    public List<ProductVariantAttribute> getVariantAttributes() {
        return null;
    }

    @Override
    public List<ProductVariantAttributeType> getVariantAttributeTypes() {
        return null;
    }
}
