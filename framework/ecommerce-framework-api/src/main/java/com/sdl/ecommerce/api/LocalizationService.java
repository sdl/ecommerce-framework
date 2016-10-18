package com.sdl.ecommerce.api;

import java.net.URI;
import java.util.Map;

/**
 * Localization Service.
 * Gives access to localization specific parameters.
 * The idea with this service is to hide underlying implementation (in this case DXA WebRequestContext Localization).
 *
 * @author nic
 */
public interface LocalizationService {

    /**
     * @return locale
     */
    String getLocale();

    /**
     * Get localizated configuration property such as site ID, shop name etc.
     *
     * @param name
     * @return config value
     */
    String getLocalizedConfigProperty(String name);

    /**
     * Get all available claims from the context broker.
     * @return claims
     */
    Map<URI, Object> getAllClaims();
}
