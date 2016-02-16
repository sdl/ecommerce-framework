package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.impl.AbstractInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Demandware Module Initializer
 *
 * @author nic
 */
@Component
public class DemandwareModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Demandware";

    @PostConstruct
    public void initialize() throws Exception {

        this.registerViewModel("ExternalContentLibraryStubSchemademandware", ECommerceEclItem.class);
    }

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}
