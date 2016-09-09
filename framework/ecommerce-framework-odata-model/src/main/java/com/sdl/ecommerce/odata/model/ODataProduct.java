package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.io.Serializable;
import java.util.*;

/**
 * OData Product
 *
 * @author nic
 */
@EdmEntity(name="Product", key = "id", namespace = "SDL.ECommerce")
@EdmEntitySet(name="Products")
public class ODataProduct implements Product, Serializable {

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

    public ODataProduct() {}

    public ODataProduct(Product product) {
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

    // TODO: IMPLEMENT !!!
    @Override
    public List<Category> getCategories() {
        return Collections.emptyList();
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

}
