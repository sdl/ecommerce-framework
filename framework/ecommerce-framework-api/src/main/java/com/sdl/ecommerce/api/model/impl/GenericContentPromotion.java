package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ContentPromotion;
import com.sdl.ecommerce.api.model.Location;

/**
 * Generic ContentPromotion implementation
 *
 * @author nic
 */
public class GenericContentPromotion extends GenericPromotion implements ContentPromotion {

    private String text;
    private String imageUrl;
    private Location location;

    public GenericContentPromotion(String id, String name, String title, String text, String imageUrl, Location location) {
        super(id, name, title);
        this.text = text;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public GenericContentPromotion(String id, String name, String title, String text, String imageUrl, Location location, String editUrl) {
        super(id, name, title, editUrl);
        this.text = text;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }
}
