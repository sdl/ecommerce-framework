package com.sdl.ecommerce.api.model;

import com.sdl.ecommerce.api.model.ContentPromotion;

import java.util.List;

/**
 * Image Map Promotion
 *
 * @author nic
 */
public interface ImageMapPromotion extends ContentPromotion {

    /**
     * Class representing a clickable content area within the image.
     */
    class ContentArea {
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private Location location;

        public ContentArea(int x1, int y1, int x2, int y2, Location location) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }

        public int getX1() {
            return x1;
        }

        public int getX2() {
            return x2;
        }

        public int getY1() {
            return y1;
        }

        public int getY2() {
            return y2;
        }
    }

    /**
     * Get all defined content areas.
     * @return list of areas
     */
    List<ContentArea> getContentAreas();
}
