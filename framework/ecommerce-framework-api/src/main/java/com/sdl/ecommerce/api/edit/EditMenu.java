package com.sdl.ecommerce.api.edit;

import java.util.List;

/**
 * In-Context Edit Menu
 *
 * @author nic
 */
public interface EditMenu {

    /**
     * @return title
     */
    String getTitle();

    /**
     * @return menu items
     */
    List<MenuItem> getMenuItems();

}
