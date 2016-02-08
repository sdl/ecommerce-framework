package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Product;
import com.sdl.webapp.common.api.mapping.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.RichText;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.Image;

import static com.sdl.webapp.common.api.mapping.config.SemanticVocabulary.SDL_CORE;

/**
 * ProductDetailWidget
 *
 * @author nic
 */
@SemanticEntity(entityName = "ProductDetailWidget", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ProductDetailWidget extends AbstractEntityModel {

    private Product product;

    // Overwriteable properties

    @SemanticProperty("e:title")
    private String title;

    @SemanticProperty("e:description")
    private RichText description;

    @SemanticProperty("e:image")
    private Image image;

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
