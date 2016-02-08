package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.location.Location;

/**
 * Fredhopper Link Manager
 *
 * @author nic
 */
public interface FredhopperLinkManager {

    String convertToSEOLink(Location location);

    String convertToSEOLink(String location);

    String processImageUrl(String imageUrl);

}
