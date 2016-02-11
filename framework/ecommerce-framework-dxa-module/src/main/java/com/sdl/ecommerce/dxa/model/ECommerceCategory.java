package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.EclItem;

import static com.sdl.webapp.common.api.mapping.config.SemanticVocabulary.SDL_CORE;

/**
 * ECommerceCategory
 *
 * @author nic
 */
@SemanticEntity(entityName = "ECommerceCategory", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ECommerceCategory extends AbstractEntityModel {

    @SemanticProperty("e:categoryPath")
    private String categoryPath;

    @SemanticProperty("e:categoryRef")
    private ECommerceEclItem categoryRef;

    public String getCategoryPath() {
        return categoryPath;
    }

    public ECommerceEclItem getCategoryRef() {
        return categoryRef;
    }
}
