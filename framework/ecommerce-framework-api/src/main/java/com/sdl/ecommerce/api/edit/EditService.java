package com.sdl.ecommerce.api.edit;

import com.sdl.ecommerce.api.Query;

/**
 * Edit Service
 *
 * Provides information about what can be edited inline. Is optional to implement.
 *
 * @author nic
 */
public interface EditService {

    /**
     * Get in-context menu items for specified query
     *
     * @param query
     * @return menu items
     */
    EditMenu getInContextMenuItems(Query query);

    // GetNonvisibleEditableItems (modifications etc)
}
