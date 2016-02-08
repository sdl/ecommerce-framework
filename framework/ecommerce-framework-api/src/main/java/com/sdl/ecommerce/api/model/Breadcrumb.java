package com.sdl.ecommerce.api.model;

/**
 * Breadcrumb
 *
 * @author nic
 */
public interface Breadcrumb {

    String getTitle();

    String getUrl();

    boolean isCategory();
}
