package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Product;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.RichText;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.Image;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Product Detail Widget
 *
 * @author nic
 */
@SemanticEntity(entityName = "ProductDetailWidget", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ProductDetailWidget extends AbstractEntityModel {

    @SemanticProperty("e:product")
    private ECommerceProductReference productReference;

    private Product product;

    // Properties that can overwrite the information read from the E-Commerce system
    //
    @SemanticProperty("e:title")
    private String title;

    @SemanticProperty("e:description")
    private RichText description;

    @SemanticProperty("e:image")
    private Image image;

    public ECommerceProductReference getProductReference() {
        return productReference;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public RichText getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }
}
