package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.model.entity.EclItem;
import com.sdl.webapp.common.impl.AbstractInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * HybrisModuleInitializer
 *
 * @author nic
 */
@Component
public class HybrisModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Hybris";

    @PostConstruct
    public void initialize() throws Exception {

        this.registerViewModel("ExternalContentLibraryStubSchemahybris", ECommerceEclItem.class);
    }

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}
