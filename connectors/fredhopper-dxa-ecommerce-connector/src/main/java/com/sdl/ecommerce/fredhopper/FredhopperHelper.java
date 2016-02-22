package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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

    static public Map<String,String> getProductModelMappings(LocalizationService localizationService) {
        String modelMappingsString = localizationService.getLocalizedConfigProperty("fredhopper-productModelMappings");
        Map<String,String> modelMappings = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(modelMappingsString, "=; ");
        while ( tokenizer.hasMoreTokens() ) {
            String modelName = tokenizer.nextToken();
            String fredhopperName = tokenizer.nextToken();
            modelMappings.put(modelName, fredhopperName);
        }
        return modelMappings;
    }

}
