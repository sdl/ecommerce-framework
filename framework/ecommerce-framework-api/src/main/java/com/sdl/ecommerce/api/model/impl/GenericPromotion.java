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
    private String editUrl;

    protected GenericPromotion(String id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    protected GenericPromotion(String id, String name, String title, String editUrl) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.editUrl = editUrl;
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

    @Override
    public String getEditUrl() {
        return this.editUrl;
    }
}
