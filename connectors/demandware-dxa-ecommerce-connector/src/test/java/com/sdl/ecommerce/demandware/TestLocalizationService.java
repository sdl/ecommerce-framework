package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.beans.factory.annotation.Value;

/**
 * TestLocalizationService
 *
 * @author nic
 */
public class TestLocalizationService implements LocalizationService {

    @Value("${demandware.locale}")
    private String locale;

    @Value("${demandware.siteId}")
    private String siteId;

    @Value("${demandware.currency}")
    private String currency;

    @Override
    public String getLocale() {
        return this.locale;
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        if ( name.equals("demandware-siteId") ) {
            return this.siteId;
        }
        else if ( name.equals("demandware-locale") ) {
            return this.locale;
        }
        else if ( name.equals("demandware-currency") ) {
            return this.currency;
        }
        else {
            return null;
        }
    }

    @Override
    public String getPublicationId() {
        return "1";
    }
}
