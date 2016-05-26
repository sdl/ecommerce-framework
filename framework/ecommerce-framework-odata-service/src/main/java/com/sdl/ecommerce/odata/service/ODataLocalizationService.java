package com.sdl.ecommerce.odata.service;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.odata.datasource.ECommerceDataSourceProvider;
import com.sdl.odata.api.service.ODataRequestContext;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

/**
 * OData Localization Service
 *
 * @author nic
 */
@Component
@ConfigurationProperties(prefix = "localization")
public class ODataLocalizationService implements LocalizationService {

    private String defaultLocale;
    private Map<String,Map<String,String>> locales;

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public void setLocales(Map<String, Map<String, String>> locales) {
        this.locales = locales;
    }

    public Map<String, Map<String, String>> getLocales() {
        return locales;
    }

    @Override
    public Map<URI, Object> getAllClaims() {
        // TODO: How to we get all context claims here??? Or maybe just a preselected set of claims???
        // This is primarily just needed for passing triggers to Fredhopper
        return null;
    }

    @Override
    public String getLocale() {
        String locale = null;
        ODataRequestContext requestContext = ECommerceDataSourceProvider.getRequestContext();
        if ( requestContext != null ) {
            locale = requestContext.getRequest().getHeader("ECommerce-Locale");
        }
        if ( locale == null ) {
            // Use default locale
            //
            locale = this.defaultLocale;
        }
        return locale;
    }

    public boolean isLocaleDefined(String locale) {
        return this.locales.containsKey(locale);
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        Map<String,String> configProperties = locales.get(this.getLocale());
        if ( configProperties != null ) {
            return configProperties.get(name);
        }
        return null;
    }

    @Override
    public String getPublicationId() {
        // Not applicable in the micro service layer
        //
        return null;
    }

    @Override
    public String localizePath(String url) {
        // Not applicable in the micro service layer
        //
        return url;
    }
}
