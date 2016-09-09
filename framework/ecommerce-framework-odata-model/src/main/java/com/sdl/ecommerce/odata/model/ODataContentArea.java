package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ImageMapPromotion;
import com.sdl.ecommerce.api.model.Location;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataContentArea
 *
 * @author nic
 */
@EdmComplex(name = "ContentArea", namespace = "SDL.ECommerce")
public class ODataContentArea {

    @EdmProperty
    private int x1;

    @EdmProperty
    private int y1;

    @EdmProperty
    private int x2;

    @EdmProperty
    private int y2;

    @EdmProperty
    private ODataLocation location;

    public ODataContentArea() {}
    public ODataContentArea(ImageMapPromotion.ContentArea contentArea) {
        this.x1 = contentArea.getX1();
        this.y1 = contentArea.getY1();
        this.x2 = contentArea.getX2();
        this.y2 = contentArea.getY2();
        this.location = new ODataLocation(contentArea.getLocation());
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public Location getLocation() {
        return location;
    }

    public ImageMapPromotion.ContentArea toContentArea() {
        return new ImageMapPromotion.ContentArea(this.x1, this.y1, this.x2, this.y2, this.location);
    }
}
