package com.sdl.ecommerce.fredhopper.model;

import com.fredhopper.webservice.client.Attribute;
import com.fredhopper.webservice.client.Item;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fredhopper Product
 *
 * @author nic
 */
public class FredhopperProduct implements Product {

    private Item item;
    private Map<String,Object> attributes = new HashMap<>();
    // TODO: Replace this with a single list of attribute values.
    // Or use the similar structure as the ProductVariantAttribute
    //
    private Map<String,Attribute> fhAttributes = new HashedMap();
    private List<Category> categories;
    private FredhopperLinkManager linkManager;
    private Map<String,String> modelMappings;
    private LocalizationService localizationService;
    private List<ProductVariantAttribute> variantAttributes;
    private List<ProductVariantAttributeType> variantAttributeTypes;

    /**
     * Create a Fredhopper product representation. The provided model mappings are used to map Fredhopper product attributes
     * to the standard E-Commerce Framework model
     * @param item
     * @param linkManager
     * @param modelMappings
     * @param localizationService
     */
    public FredhopperProduct(Item item,
                             FredhopperLinkManager linkManager,
                             Map<String,String> modelMappings,
                             LocalizationService localizationService) {
        this.item = item;
        this.linkManager = linkManager;
        this.modelMappings = modelMappings;
        this.localizationService = localizationService;
    }

    @Override
    public String getId() {
        return this.item.getId();
    }

    @Override
    public String getVariantId() {
        return this.getModelAttribute("variantId", true);
    }

    @Override
    public String getName() {
        return this.getModelAttribute("name");
    }

    @Override
    public String getDescription() {
        return this.getModelAttribute("description");
    }

    @Override
    public ProductPrice getPrice() {
        String fredhopperAttributeName = this.modelMappings.get("price");
        if ( fredhopperAttributeName != null ) {
            Attribute fhPrice = this.fhAttributes.get(fredhopperAttributeName);
            if ( fhPrice != null && !fhPrice.getValue().isEmpty() ) {
                float price = Float.parseFloat(fhPrice.getValue().get(0).getNonMl());
                String formattedPrice = fhPrice.getValue().get(0).getValue();
                return new FredhopperProductPrice(price, formattedPrice);
            }
        }
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        String thumbnailUrl = this.getModelAttribute("thumbnailUrl");
        if ( thumbnailUrl == null ) {
            return this.getPrimaryImageUrl();
        }
        return this.linkManager.processImageUrl(thumbnailUrl);
    }

    @Override
    public String getPrimaryImageUrl() {
        String imageUrl = this.getModelAttribute("primaryImageUrl");
        if ( imageUrl != null ) {
            imageUrl = this.linkManager.processImageUrl(imageUrl);
        }
        return imageUrl;
    }

    @Override
    public List<Category> getCategories() {
        if ( this.categories == null ) {
            synchronized (this) {
                if (this.categories == null) {
                    this.categories = new ArrayList<>();
                }
            }
        }
        return this.categories;
    }

    @Override
    public Map<String,Object> getAttributes() {
        return this.attributes;
    }


    @Override
    public List<ProductVariant> getVariants() {
        return null;
    }

    @Override
    public List<ProductVariantAttribute> getVariantAttributes() {
        return this.variantAttributes;
    }

    @Override
    public List<ProductVariantAttributeType> getVariantAttributeTypes() {
        return this.variantAttributeTypes;
    }

    public void setVariantAttributes(List<ProductVariantAttribute> variantAttributes) {
        this.variantAttributes = variantAttributes;
    }

    public void setVariantAttributeTypes(List<ProductVariantAttributeType> variantAttributeTypes) {
        this.variantAttributeTypes = variantAttributeTypes;
    }

    public Item getFredhopperItem() {
        return this.item;
    }

    public void addFredhopperAttribute(String name, Attribute attribute) {
        this.fhAttributes.put(name, attribute);
    }

    public Attribute getFredhopperAttribute(String name) {
        return this.fhAttributes.get(name);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }


    private String getModelAttribute(String name) {
        return this.getModelAttribute(name, false);
    }

    private String getModelAttribute(String name, boolean singleValue) {
        String fredhopperAttribute = this.modelMappings.get(name);
        Object fredhopperValue = null;
        String fredhopperStringValue = null;
        if ( fredhopperAttribute != null ) {
            fredhopperValue = this.attributes.get(fredhopperAttribute);
            if ( fredhopperValue instanceof String ) {
                fredhopperStringValue = (String) fredhopperValue;
            }
            else if ( fredhopperValue instanceof List ) {
                List<String> list = (List<String>) fredhopperValue;
                if ( list.size() > 0 ) {
                    if ( list.size() > 1 && singleValue ) {
                        // When having a list of values when expecting one single value
                        //
                        return null;
                    }
                    fredhopperStringValue = list.get(0); // TODO: What to select if there is multiple values here?
                }
            }
        }
        return fredhopperStringValue;
    }
}
