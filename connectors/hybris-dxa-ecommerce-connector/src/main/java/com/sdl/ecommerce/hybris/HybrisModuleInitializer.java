package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.views.*;
import com.sdl.webapp.common.api.model.entity.EclItem;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Hybris Module Initializer
 *
 * @author nic
 */
@Component
@RegisteredViewModels({
        @RegisteredViewModel(viewName = "ExternalContentLibraryStubSchemahybris", modelClass = HybrisEclItem.class)
})
public class HybrisModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Hybris";

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }

}
