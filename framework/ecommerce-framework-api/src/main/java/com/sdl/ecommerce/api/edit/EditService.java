package com.sdl.ecommerce.api.edit;

import com.sdl.ecommerce.api.ViewType;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.webapp.common.api.localization.Localization;

import java.util.Map;

/**
 * Edit Service
 *
 * Provides information about what can be edited inline. Is optional to implement.
 *
 * @author nic
 */
public interface EditService {

    enum MenuType {
        CREATE_NEW
    }

    /**
     * Get in-context menu items for specified category and menu type.
     *
     * @param menuType
     * @param localization
     * @return menu items
     */
    EditMenu getInContextMenuItems(Category category, MenuType menuType, Localization localization);

    // GetNonvisibleEditableItems (modifications etc)
}
