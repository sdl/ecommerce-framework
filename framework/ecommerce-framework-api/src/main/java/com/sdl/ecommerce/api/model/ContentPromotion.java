package com.sdl.ecommerce.api.model;

/**
 * Content Promotion
 *
 * @author nic
 */
public interface ContentPromotion extends Promotion {

    // TODO: Should we point to a Tridion based content in this case???

    /**
     * Get textual content of this promotion
     * @return text
     */
    String getText();

    /**
     * Get image URL to the image to be used in this promotion
     * @return url
     */
    String getImageUrl();

    /**
     * Get call-to-action link for this promotion.
     * @return link
     */
    String getLink();

}
