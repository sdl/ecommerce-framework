package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * ECommerce Product Refererence
 *
 * @author nic
 */
@SemanticEntity(entityName = "ECommerceProduct", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ECommerceProductReference extends AbstractEntityModel {

    @SemanticProperty("e:productId")
    private String productId;

    @SemanticProperty("e:productRef")
    private ECommerceEclItem productRef;

    public String getProductId() {
        return productId;
    }

    public ECommerceEclItem getProductRef() {
        return productRef;
    }

}
