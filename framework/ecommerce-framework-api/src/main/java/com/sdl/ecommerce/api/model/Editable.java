package com.sdl.ecommerce.api.model;

/**
 * Editable
 *
 * Markes a specific E-Commerce entity (facet, promotion etc) as editable. When entering XPM
 * the provided edit URL is used to generate in-context edit controls.
 *
 * @author nic
 */
public interface Editable {

    /**
     * Returns edit URL to either custom DXA dialog or a wrapper around existing edit GUI in the E-Commerce system
     * @return URL
     */
    String getEditUrl();

}
