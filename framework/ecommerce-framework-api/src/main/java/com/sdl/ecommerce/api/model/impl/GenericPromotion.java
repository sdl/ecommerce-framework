package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Promotion;

/**
 * GenericPromotion
 *
 * @author nic
 */
public abstract class GenericPromotion implements Promotion {

    private String id;
    private String name;
    private String title;

    protected GenericPromotion(String id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
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
    public String getTitle() {
        return this.title;
    }
}
