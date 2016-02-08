package com.sdl.ecommerce.api.model;

/**
 * Content Promotion
 *
 * @author nic
 */
public interface ContentPromotion extends Promotion {

    // TODO: Should we point to a Tridion based content in this case???

    String getText();

    String getImageUrl();

    String getLink();

}
