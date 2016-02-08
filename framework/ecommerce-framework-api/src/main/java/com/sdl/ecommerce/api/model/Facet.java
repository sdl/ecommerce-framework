package com.sdl.ecommerce.api.model;

/**
 * Facet
 *
 * @author nic
 */
public interface Facet {

    String getTitle();

    String getUrl();

    int getCount();

    boolean isSelected();
}
