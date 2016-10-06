package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.ecommerce.dxa.model.*;
import com.sdl.webapp.common.api.mapping.views.AbstractInitializer;
import com.sdl.webapp.common.api.mapping.views.ModuleInfo;
import com.sdl.webapp.common.api.mapping.views.RegisteredViewModel;
import com.sdl.webapp.common.api.mapping.views.RegisteredViewModels;
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
@ModuleInfo(name = "E-Commerce module", areaName = "ECommerce", description = "Module for the E-Commerce framework")
@RegisteredViewModels({

        // E-Commerce Widget Views
        //
        @RegisteredViewModel(viewName = "Breadcrumb", modelClass = BreadcrumbWidget.class),
        @RegisteredViewModel(viewName = "CartDetail", modelClass = CartWidget.class),
        @RegisteredViewModel(viewName = "CartMinimized", modelClass = CartWidget.class),
        @RegisteredViewModel(viewName = "DetailBreadcrumb", modelClass = BreadcrumbWidget.class),
        @RegisteredViewModel(viewName = "Facets", modelClass = FacetsWidget.class),
        @RegisteredViewModel(viewName = "FacetsMegaNavigation", modelClass = FacetsWidget.class),
        @RegisteredViewModel(viewName = "ProductDetail", modelClass = ProductDetailWidget.class),
        @RegisteredViewModel(viewName = "ProductLister", modelClass = ProductListerWidget.class),
        @RegisteredViewModel(viewName = "Promotions", modelClass = PromotionsWidget.class),
        @RegisteredViewModel(viewName = "SearchBox", modelClass = SearchBox.class),
        @RegisteredViewModel(viewName = "SearchFeedback", modelClass = SearchFeedbackWidget.class),

        // E-Commerce ECL Views
        //
        @RegisteredViewModel(viewName = "ProductDetailEclItem", modelClass = ECommerceEclItem.class)
})

public class ECommerceModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "ECommerce";

    @Autowired
    private PluggableMarkupRegistry pluggableMarkupRegistry;

    @PostConstruct
    public void initialize() {

        // Register additional JS to be used on pages
        //
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