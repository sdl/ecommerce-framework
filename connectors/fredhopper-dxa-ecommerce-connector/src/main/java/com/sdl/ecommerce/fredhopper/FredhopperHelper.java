package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;

/**
 * FredhopperHelper
 *
 * @author nic
 */
public abstract class FredhopperHelper {

    static public String getLocale(LocalizationService localizationService) {
        String locale = localizationService.getLocalizedConfigProperty("fredhopper-locale");
        if ( locale == null ) {
            locale = localizationService.getLocale();
        }
        return locale;
    }

    static public String getUniverse(LocalizationService localizationService) {
        return localizationService.getLocalizedConfigProperty("fredhopper-universe");
    }

}
