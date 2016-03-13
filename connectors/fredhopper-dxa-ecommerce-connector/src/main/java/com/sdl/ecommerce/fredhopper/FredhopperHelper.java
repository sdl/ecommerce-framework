package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.webapp.common.api.localization.Localization;

import java.util.*;

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

    static public List<String> getHiddenFacetValues(LocalizationService localizationService) {
        return getConfigList(localizationService, "fredhopper-hiddenFacetValues");
    }

    // Not used right now.
    static public List<String> getFlyoutFacets(LocalizationService localizationService) {
        return getConfigList(localizationService, "fredhopper-flyoutFacets");
    }

    static private List<String> getConfigList(LocalizationService localizationService, String name) {
        String configListString = localizationService.getLocalizedConfigProperty(name);
        if ( configListString != null ) {
            StringTokenizer tokenizer = new StringTokenizer(configListString, ",");
            List<String> values = new ArrayList<>();
            while ( tokenizer.hasMoreTokens() ) {
                values.add(tokenizer.nextToken());
            }
            return values;
        }
        return null;
    }

}
