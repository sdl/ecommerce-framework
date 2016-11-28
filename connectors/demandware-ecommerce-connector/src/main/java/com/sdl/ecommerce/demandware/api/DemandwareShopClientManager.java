package com.sdl.ecommerce.demandware.api;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * DemandwareShop Client Manager
 * Manage Demandware API clients for specific localization/sites.
 *
 * @author nic
 */
@Component
public class DemandwareShopClientManager {

    @Autowired
    private LocalizationService localizationService;

    @Value("${demandware.url}")
    private String url;

    @Value("${demandware.clientId}")
    private String clientId;

    @Value("${demandware.overriddenOrigin:null}")
    private String overriddenOrigin = null;

    @Value("${demandware.trustAllSSLCerts}")
    private boolean trustAllSSLCerts = false;

    private Map<String, DemandwareShopClient> shopClients = new HashMap<>();

    /**
     * Get instance for current localization.
     * @return instance
     */
    public DemandwareShopClient getInstance() {

        DemandwareShopClient shopClient = this.shopClients.get(this.localizationService.getLocale());
        if ( shopClient == null ) {
            shopClient = this.createShopClient();
        }
        return shopClient;
    }

    /**
     * Create new shop client for current localization
     * @return client
     */
    private synchronized DemandwareShopClient createShopClient() {
        String shopUrl = this.url + this.getSiteId();
        DemandwareShopClient shopClient = new DemandwareShopClientImpl(shopUrl, clientId, this.getLocale(), this.getCurrency(), overriddenOrigin, trustAllSSLCerts);
        this.shopClients.put(this.localizationService.getLocale(), shopClient);
        return shopClient;
    }

    private String getSiteId() {
        return this.localizationService.getLocalizedConfigProperty("demandware-siteId");
    }

    private String getLocale() {
        String locale = this.localizationService.getLocalizedConfigProperty("demandware-locale");
        if ( locale == null ) {
            locale = this.localizationService.getLocale();
        }
        return locale;
    }

    private String getCurrency() {
        return this.localizationService.getLocalizedConfigProperty("demandware-currency");
    }
}
