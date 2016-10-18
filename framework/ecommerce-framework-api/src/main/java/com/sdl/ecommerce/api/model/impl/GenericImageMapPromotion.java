package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.ImageMapPromotion;
import com.sdl.ecommerce.api.model.Location;

import java.util.List;

/**
 * ImageMapPromotion
 *
 * @author nic
 */
public class GenericImageMapPromotion extends GenericContentPromotion implements ImageMapPromotion {

    private List<ContentArea> contentAreas;

    public GenericImageMapPromotion(String id, String name, String title, String imageUrl, List<ContentArea> contentAreas) {
        super(id, name, title, null, imageUrl, null);
        this.contentAreas = contentAreas;
    }

    public GenericImageMapPromotion(String id, String name, String title, String imageUrl, String editUrl, List<ContentArea> contentAreas) {
        super(id, name, title, null, imageUrl, null, editUrl);
        this.contentAreas = contentAreas;
    }

    @Override
    public List<ContentArea> getContentAreas() {
        return contentAreas;
    }
}
