package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

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
    private List<Category> categories;
    private FredhopperLinkManager linkManager;
    private Map<String,String> modelMappings;

    /**
     * Create a Fredhopper product representation. The provided model mappings are used to map Fredhopper product attributes
     * to the standard E-Commerce Framework model
     * @param id
     * @param linkManager
     * @param modelMappings
     */
    public FredhopperProduct(String id, FredhopperLinkManager linkManager, Map<String,String> modelMappings) {
        this.id = id;
        this.linkManager = linkManager;
        this.modelMappings = modelMappings;
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
        return new FredhopperProductPrice(this.getModelAttribute("price"));
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
        return this.linkManager.processImageUrl(imageUrl);
    }

    @Override
    public String getDetailPageUrl() {
        String name = this.getName();
        if ( name != null ) {
            // Generate a SEO friendly URL
            //
            String seoName = this.getName().toLowerCase().replace(" ", "-").replace("'", "").replace("--", "");
            return "/p/" + seoName + "/" + this.id;
        }
        return "/p/" + this.id;
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
