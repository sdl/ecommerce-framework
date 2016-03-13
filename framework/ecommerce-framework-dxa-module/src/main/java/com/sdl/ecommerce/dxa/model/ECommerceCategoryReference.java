package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * ECommerce Category Reference
 *
 * @author nic
 */
@SemanticEntity(entityName = "ECommerceCategory", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ECommerceCategoryReference extends AbstractEntityModel {

    @SemanticProperty("e:categoryPath")
    private String categoryPath;

    @SemanticProperty("e:categoryRef")
    private ECommerceEclItem categoryRef;

    @SemanticProperty("e:categoryId")
    private String categoryId;

    // Data set by controllers
    //
    private Category category;
    private String categoryUrl;

    public String getCategoryPath() {
        return categoryPath;
    }

    public ECommerceEclItem getCategoryRef() {
        return categoryRef;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }
}
