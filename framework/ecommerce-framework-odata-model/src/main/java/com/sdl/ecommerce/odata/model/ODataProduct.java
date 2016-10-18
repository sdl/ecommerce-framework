package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.*;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OData Product
 *
 * @author nic
 */
@EdmEntity(name="Product", key = "id", namespace = "SDL.ECommerce")
@EdmEntitySet(name="Products")
public class ODataProduct implements Product, Serializable {

    // TODO: Should breadcrumbs, promotions etc be part of this entity????

    // TODO: Should this be called ProductDetail instead and be a complex type???

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String description;

    @EdmProperty
    private ODataProductPrice price;

    @EdmProperty
    private String thumbnailUrl;

    @EdmProperty
    private String primaryImageUrl;

    @EdmProperty(name = "attributes")
    private List<ODataProductAttribute> attributes;

    private Map<String,Object> attributeMap = null;

    @EdmProperty
    private List<ODataCategorySummary> categories = new ArrayList<>();

    @EdmProperty
    private List<ODataPromotion> promotions = new ArrayList<>();

    @EdmProperty
    private List<ODataBreadcrumb> breadcrumbs = new ArrayList<>();

    public ODataProduct() {}

    public ODataProduct(ProductDetailResult detailResult) {
        Product product = detailResult.getProductDetail();
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = new ODataProductPrice(product.getPrice());
        this.thumbnailUrl = product.getThumbnailUrl();
        this.primaryImageUrl = product.getPrimaryImageUrl();
        Map<String,Object> attributes = product.getAttributes();
        if ( attributes != null ) {
            this.attributes = new ArrayList<>();
            for ( String name : attributes.keySet() ) {
                this.attributes.add(new ODataProductAttribute(name, attributes.get(name)));
            }
        }
        if ( product.getCategories() != null ) {
            product.getCategories().forEach(category -> this.categories.add(new ODataCategorySummary(category)));
        }
        if ( detailResult.getPromotions() != null ) {
            detailResult.getPromotions().forEach(promotion -> this.promotions.add(new ODataPromotion(promotion)));
        }
        if ( detailResult.getBreadcrumbs() != null ) {
            detailResult.getBreadcrumbs().forEach(breadcrumb -> this.breadcrumbs.add(new ODataBreadcrumb(breadcrumb)));
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProductPrice getPrice() {
        return this.price;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    @Override
    public String getPrimaryImageUrl() {
        return this.primaryImageUrl;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories.stream().collect(Collectors.toList());
    }

    @Override
    public List<FacetParameter> getFacets() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, Object> getAttributes() {
        if ( this.attributeMap == null ) {
            this.attributeMap = new HashMap<>();
            if ( this.attributes != null ) {
                for ( ODataProductAttribute attribute : this.attributes ) {
                    Object value;
                    if ( attribute.getSingleValue() != null ) {
                        value = attribute.getSingleValue();
                    }
                    else {
                        value = attribute.getMultiValue();
                    }
                    this.attributeMap.put(attribute.getName(), value);
                }
            }
        }
        return this.attributeMap;
    }

    public List<Promotion> getPromotions() {
        return promotions.stream().collect(Collectors.toList());
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs.stream().collect(Collectors.toList());
    }
}
