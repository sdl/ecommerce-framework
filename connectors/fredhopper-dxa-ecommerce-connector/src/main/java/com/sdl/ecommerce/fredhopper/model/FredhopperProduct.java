package com.sdl.ecommerce.fredhopper.model;

import com.fredhopper.webservice.client.Attribute;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;
import com.sdl.webapp.common.api.localization.Localization;
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

    private String id;
    private List<FacetParameter> facets;
    private Map<String,Object> attributes = new HashMap<>();
    // TODO: Replace this with a single list of attribute values.
    private Map<String,Attribute> fhAttributes = new HashedMap();
    private List<Category> categories;
    private FredhopperLinkManager linkManager;
    private Map<String,String> modelMappings;
    private LocalizationService localizationService;

    /**
     * Create a Fredhopper product representation. The provided model mappings are used to map Fredhopper product attributes
     * to the standard E-Commerce Framework model
     * @param id
     * @param linkManager
     * @param modelMappings
     * @param localizationService
     */
    public FredhopperProduct(String id,
                             FredhopperLinkManager linkManager,
                             Map<String,String> modelMappings,
                             LocalizationService localizationService) {
        this.id = id;
        this.linkManager = linkManager;
        this.modelMappings = modelMappings;
        this.localizationService = localizationService;
    }

    @Override
    public String getId() {
        return id;
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
    public List<FacetParameter> getFacets() {
        return facets;
    }

    @Override
    public Map<String,Object> getAttributes() {
        return this.attributes;
    }


    public void addFredhopperlAttribute(String name, Attribute attribute) {
        this.fhAttributes.put(name, attribute);
    }

    public void setFacets(List<FacetParameter> facets) {
        this.facets = facets;
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    private String getModelAttribute(String name) {
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
                    fredhopperStringValue = list.get(0); // TODO: What to select if there is multiple values here?
                }
            }
        }
        return fredhopperStringValue;
    }
}
