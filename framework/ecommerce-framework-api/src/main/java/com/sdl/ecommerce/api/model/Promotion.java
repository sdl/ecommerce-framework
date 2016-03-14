package com.sdl.ecommerce.api.model;

/**
 * Promotion
 * Base interface for all kind of promotions driven by an E-Commerce system.
 *
 * @author nic
 */
public interface Promotion {

    /**
     * Get unique identifier of the promotion.
     *
     * @return id
     */
    String getId();

    /**
     * Get name of the promotion.
     *
     * @return name
     */
    String getName();

    /**
     * Get title of the promotion. This can be localized based on current langauge.
     * @return title
     */
    String getTitle();

}
