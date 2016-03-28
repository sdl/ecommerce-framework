package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.util.Map;

/**
 * TestLocalizationService
 *
 * @author nic
 */
public class TestLocalizationService implements LocalizationService {

    @Value("${hybris.siteId}")
    private String siteId;

    @Value("${hybris.activeServiceCatalog}")
    private String activeServiceCatalog;

    @Value("${hybris.language}")
    private String language;

    @Value("${hybris.currency}")
    private String currency;

    @Value("${hybris.catalogBranch}")
    private String catalogBranch;

    @Value("${hybris.excludeFacets}")
    private String excludeFacets;

    @Value("${hybris.flyoutFacets}")
    private String flyoutFacets;


    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public String getPublicationId() {
        return "1";
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        if ( name.equals("hybris-siteId") ) {
            return this.siteId;
        }
        else if ( name.equals("hybris-activeServiceCatalog") ) {
            return this.activeServiceCatalog;
        }
        else if ( name.equals("hybris-catalogBranch") ) {
            return this.catalogBranch;
        }
        else if ( name.equals("hybris-language") ) {
            return this.language;
        }
        else if ( name.equals("hybris-currency") ) {
            return this.currency;
        }
        else if ( name.equals("hybris-excludeFacets") ) {
            return this.excludeFacets;
        }
        else if ( name.equals("hybris-flyoutFacets") ) {
            return this.flyoutFacets;
        }
        else {
            return null;
        }
    }

    @Override
    public String localizePath(String url) {
        return url;
    }

    @Override
    public Map<URI, Object> getAllClaims() {
        return null;
    }
}
