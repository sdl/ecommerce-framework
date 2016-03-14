package com.sdl.ecommerce.dxa;

import com.sdl.webapp.common.api.mapping.views.AbstractInitializer;
import com.sdl.webapp.common.markup.PluggableMarkupRegistry;
import com.sdl.webapp.common.markup.html.builders.HtmlBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * ECommerce Module Initializer
 *
 * @author nic
 */
@Component
public class ECommerceModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "ECommerce";

    @Autowired
    private PluggableMarkupRegistry pluggableMarkupRegistry;

    @PostConstruct
    public void initialize() {

        // Register additional CSS/JS to be used on pages
        //
        pluggableMarkupRegistry.registerPluggableMarkup(PluggableMarkupRegistry.MarkupType.CSS,
                HtmlBuilders.element("link")
                        .withAttribute("type", "text/css")
                        .withAttribute("rel", "stylesheet")
                        .withAttribute("href", "/system/assets/css/ecommerce-styles.css").build());

        pluggableMarkupRegistry.registerPluggableMarkup(PluggableMarkupRegistry.MarkupType.BOTTOM_JS,
                HtmlBuilders.element("script").withAttribute("src", "/system/assets/scripts/ecommerce-cart.js").build());
        pluggableMarkupRegistry.registerPluggableMarkup(PluggableMarkupRegistry.MarkupType.BOTTOM_JS,
                HtmlBuilders.element("script").withAttribute("src", "/system/assets/scripts/jquery-ui.min.js").build());
    }

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}