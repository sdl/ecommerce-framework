package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import java.util.List;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Promotions Widget
 *
 * @author nic
 */
@SemanticEntity(entityName = "PromotionsWidget", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class PromotionsWidget extends AbstractEntityModel {

    @SemanticProperty("e:category")
    private ECommerceCategoryReference categoryReference;

    @SemanticProperty("e:product")
    private ECommerceProductReference productReference;

    @SemanticProperty("e:viewType")
    private String viewType;

    @SemanticProperty("e:maxPromotions")
    private Integer maxPromotions;

    private List<Promotion> promotions;

    public ECommerceCategoryReference getCategoryReference() {
        return categoryReference;
    }

    public ECommerceProductReference getProductReference() {
        return productReference;
    }

    public String getViewType() {
        return viewType;
    }

    public Integer getMaxPromotions() {
        return maxPromotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public String getViewName(Promotion promotion) {
        return promotion.getClass().getInterfaces()[0].getSimpleName();
    }

}
