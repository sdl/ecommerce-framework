package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ContentPromotion;
import com.sdl.ecommerce.api.model.ImageMapPromotion;
import com.sdl.ecommerce.api.model.Location;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ODataContentPromotion
 *
 * @author nic
 */
@EdmComplex(name = "ContentPromotion", namespace = "SDL.ECommerce")
public class ODataContentPromotion {

    @EdmProperty
    private String text;

    @EdmProperty
    private String imageUrl;

    @EdmProperty
    private ODataLocation location;

    @EdmProperty
    private List<ODataContentArea> contentAreas;

    public ODataContentPromotion() {}
    public ODataContentPromotion(ContentPromotion contentPromotion) {
        this.text = contentPromotion.getText();
        this.imageUrl = contentPromotion.getImageUrl();
        if ( contentPromotion.getLocation() != null ) {
            this.location = new ODataLocation(contentPromotion.getLocation());
        }
        this.contentAreas = new ArrayList<>();
        if ( contentPromotion instanceof ImageMapPromotion ) {
            ImageMapPromotion imageMapPromotion = (ImageMapPromotion) contentPromotion;
            if ( imageMapPromotion.getContentAreas() != null ) {
                this.contentAreas = imageMapPromotion.getContentAreas().stream().
                        map(contentArea -> new ODataContentArea(contentArea)).collect(Collectors.toList());
            }
        }
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public List<ODataContentArea> getContentAreas() {
        return contentAreas;
    }
}
