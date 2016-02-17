package com.sdl.ecommerce.fredhopper;

import com.sdl.webapp.common.api.mapping.views.AbstractInitializer;
import com.sdl.webapp.common.markup.PluggableMarkupRegistry;
import com.sdl.webapp.common.markup.html.builders.HtmlBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Fredhopper Module Initializer
 *
 * @author nic
 */
public class FredhopperModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Fredhopper";

    @Autowired
    private PluggableMarkupRegistry pluggableMarkupRegistry;

    @PostConstruct
    public void initialize() throws Exception {

        pluggableMarkupRegistry.registerPluggableMarkup(PluggableMarkupRegistry.MarkupType.BOTTOM_JS,
                HtmlBuilders.element("script").withAttribute("src", "/system/assets/scripts/fredhopper-inline-editing.js").build());
    }

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }

}
