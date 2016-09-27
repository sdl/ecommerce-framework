package com.sdl.ecommerce.demandware.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.demandware.api.model.ImageGroup;
import com.sdl.ecommerce.demandware.api.model.ProductSearchHit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demandware Product
 *
 * @author nic
 */
public class DemandwareProduct implements Product {

    private Category primaryCategory;
    private String id;
    private String name;
    private String description;
    private ProductPrice price;
    private String thumbnailUrl;
    private String primaryImageUrl;

    /**
     * Create new E-Commerce API Product based on a Demandware API product
     * @param primaryCategory
     * @param dwreProduct
     */
    public DemandwareProduct(Category primaryCategory, com.sdl.ecommerce.demandware.api.model.Product dwreProduct) {
        this.primaryCategory = primaryCategory;
        this.id = dwreProduct.getId();
        this.name = dwreProduct.getName();
        this.description = dwreProduct.getLong_description();
        this.price = new DemandwarePrice(dwreProduct.getPrice(), dwreProduct.getCurrency());
        this.thumbnailUrl = this.getImageUrl(dwreProduct, "small");
        if ( this.thumbnailUrl == null ) {
            // Fallback on large images
            //
            this.thumbnailUrl = this.getImageUrl(dwreProduct, "large");
        }
        this.primaryImageUrl = this.getImageUrl(dwreProduct, "large");

    }

    /**
     * Create a new E-Commerce API Product based on a Demandware search result hit
     * @param productSearchHit
     */
    public DemandwareProduct(ProductSearchHit productSearchHit) {
        this.id = productSearchHit.getProduct_id();
        this.name = productSearchHit.getProduct_name();
        this.price = new DemandwarePrice(productSearchHit.getPrice(), productSearchHit.getCurrency());
        if ( productSearchHit.getImage() != null ) {
            this.thumbnailUrl = productSearchHit.getImage().getLink();
            this.primaryImageUrl = productSearchHit.getImage().getLink();
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

    /**
     * Get image for specified view type
     * @param dwreProduct
     * @param viewType
     * @return image
     */
    private String getImageUrl(com.sdl.ecommerce.demandware.api.model.Product dwreProduct, String viewType) {
        for (ImageGroup imageGroup : dwreProduct.getImage_groups() ) {
            if ( imageGroup.getView_type().equals(viewType) ) {
                return imageGroup.getImages().get(0).getLink();
            }
        }
        if ( dwreProduct.getImage_groups().size() > 0 ) {
            if ( dwreProduct.getImage_groups().get(0).getImages().size() > 0 ) {
                return dwreProduct.getImage_groups().get(0).getImages().get(0).getLink();
            }
        }
        return null;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        if ( this.primaryCategory != null ) {
            categories.add(this.primaryCategory);
        }
        return categories;
    }

    @Override
    public List<FacetParameter> getFacets() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }
}
