package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;
import org.springframework.beans.factory.annotation.Value;

/**
 * TestLocalizationService
 *
 * @author nic
 */
public class TestLocalizationService implements LocalizationService {

    @Value("${fredhopper.universe")
    private String universe;

    @Value("${fredhopper.locale}")
    private String locale;

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public String getPublicationId() {
        return "1";
    }

    @Override
    public String getLocalizedConfigProperty(String name) {
        if ( name.equals("fredhopper-universe") ) {
            return universe;
        }
        else if ( name.equals("fredhopper-locale" ) ) {
            return locale;
        }
        else if ( name.equals("fredhopper-productModelMappings") ) {
            return "name=name;description=description;price=price;thumbnailUrl=_thumburl;primaryImageUrl=_imageurl";
        }
        else {
            return null;
        }
    }

    @Override
    public String localizePath(String url) {
        return url;
    }
}
