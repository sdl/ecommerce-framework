package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.views.AbstractInitializer;
import com.sdl.webapp.common.api.mapping.views.RegisteredView;
import com.sdl.webapp.common.api.mapping.views.RegisteredViews;
import com.sdl.webapp.common.api.model.entity.EclItem;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * HybrisModuleInitializer
 *
 * @author nic
 */
@Component
@RegisteredViews({
        @RegisteredView(viewName = "ExternalContentLibraryStubSchemahybris", clazz = ECommerceEclItem.class)
})
public class HybrisModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Hybris";

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}
