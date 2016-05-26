package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

/**
 * TestLocalizationService
 *
 * @author nic
 */
public class TestLocalizationService implements LocalizationService {

    @Override
    public Map<URI, Object> getAllClaims() {
        return null;
    }

    @Override
    public String getPublicationId() {
        return "1";
    }

    @Override
    public String getLocale() {
        return "en-GB";
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        return null;
    }

    @Override
    public String localizePath(String url) {
        return url;
    }
}
