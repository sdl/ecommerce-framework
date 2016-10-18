package com.sdl.webapp.navigation;

import com.sdl.webapp.common.api.mapping.views.*;
import com.sdl.webapp.common.api.model.region.RegionModelImpl;
import com.sdl.webapp.navigation.model.NavigationItem;
import com.sdl.webapp.navigation.model.NavigationSection;
import org.springframework.stereotype.Component;

/**
 * Navigation Module Initializer
 *
 * @author nic
 */
@Component
@RegisteredViewModels({
        @RegisteredViewModel(viewName = "NavigationSection", modelClass = NavigationSection.class),
        @RegisteredViewModel(viewName = "NavigationItem", modelClass = NavigationItem.class),
        @RegisteredViewModel(viewName = "MegaNavigation", modelClass = RegionModelImpl.class)
})
public class NavigationModuleInitializer extends AbstractInitializer {

    private static final String AREA_NAME = "Navigation";

    // TODO: Make a Broker navigation provider that picks items through Broker DB
    //

    @Override
    protected String getAreaName() {
        return AREA_NAME;
    }
}
