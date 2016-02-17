package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.model.Cart;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.Link;
import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Cart Widget
 *
 * @author nic
 */
@SemanticEntity(entityName = "CartWidget", vocabulary = SDL_CORE, prefix = "e", public_ = true)
public class CartWidget extends AbstractEntityModel {

    @SemanticProperty("e:_self")
    private String cartPageLink;

    @SemanticProperty("e:checkoutLink")
    private Link checkoutLink;

    private Cart cart;

    public String getCartPageLink() {
        return cartPageLink;
    }

    public Link getCheckoutLink() {
        return checkoutLink;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getCartCount() {
        if ( this.cart != null ) {
            return this.cart.count();
        }
        else {
            return 0;
        }
    }
}
