package com.sdl.ecommerce.api;

/**
 * Localization Service.
 * Gives access to localization specific parameters.
 * The idea with this service is to hide underlying implementation (in this case DXA WebRequestContext Localization).
 *
 * @author nic
 */
public interface LocalizationService {

    /**
     * @return publication ID
     */
    String getPublicationId();

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
}
