package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.webapp.common.api.mapping.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import java.util.List;

import static com.sdl.webapp.common.api.mapping.config.SemanticVocabulary.SDL_CORE;

/**
 * Promotions Widget
 *
 * @author nic
 */
@SemanticEntity(entityName = "PromotionsWidget", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class PromotionsWidget extends AbstractEntityModel {

    @SemanticProperty("e:category")
    private String category;

    @SemanticProperty("e:viewType")
    private String viewType;

    @SemanticProperty("e:maxPromotions")
    private Integer maxPromotions;

    private List<Promotion> promotions;

    public String getCategory() {
        return category;
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
