package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.views.*;
import org.springframework.stereotype.Component;

/**
 * Demandware Module Initializer
 *
 * @author nic
 */
@Component
@RegisteredViewModels({
        @RegisteredViewModel(viewName = "ExternalContentLibraryStubSchemademandware", modelClass = DemandwareEclItem.class)
})
public class DemandwareModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Demandware";

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}
