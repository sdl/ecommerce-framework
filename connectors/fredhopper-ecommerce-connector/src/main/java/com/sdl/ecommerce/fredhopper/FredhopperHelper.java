package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;

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

    static public List<String> getAggregatedFacets(LocalizationService localizationService) {
        return getConfigList(localizationService, "fredhopper-aggregatedFacets");
    }

    // Not used right now.
    static public List<String> getFlyoutFacets(LocalizationService localizationService) {
        return getConfigList(localizationService, "fredhopper-flyoutFacets");
    }

    static public Map<String,String> getTriggers(LocalizationService localizationService) {
        /*  TODO: FIX TRIGGER MAPPINGS TOWARDS NEW CLAIM STORE
        String triggerMappings = localizationService.getLocalizedConfigProperty("fredhopper-triggerMappings");
        Map<URI,Object> claims = localizationService.getAllClaims();
        if ( triggerMappings != null && claims != null  ) {
            Map<String,String> triggers = new HashMap<>();
            StringTokenizer tokenizer = new StringTokenizer(triggerMappings, "=; ");
            while ( tokenizer.hasMoreTokens() ) {
                String claim = tokenizer.nextToken();
                String trigger = tokenizer.nextToken();
                Object claimValue = claims.get(URI.create(claim));
                if ( claimValue != null ) {
                    triggers.put(trigger, claimValue.toString());
                }
            }
            return triggers;
        }
        */
        return null;
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
