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
        String seoName = this.getName().toLowerCase().replace(" ", "-").replace("'", "").replace("--", "");
        return "/p/" + seoName + "/" + this.id;
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
        String fredhopperValue = null;
        if ( fredhopperAttribute != null ) {
              fredhopperValue = (String) this.attributes.get(fredhopperAttribute);
        }
        return fredhopperValue;
    }
}
