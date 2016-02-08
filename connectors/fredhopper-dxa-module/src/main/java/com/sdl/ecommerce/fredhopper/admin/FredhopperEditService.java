package com.sdl.ecommerce.fredhopper.admin;

import com.sdl.ecommerce.api.edit.EditMenu;
import com.sdl.ecommerce.api.edit.EditService;
import com.sdl.ecommerce.api.edit.MenuItem;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.fredhopper.FredhopperClient;
import com.sdl.webapp.common.api.localization.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * FredhopperEditService
 *
 * @author nic
 */
@Component
public class FredhopperEditService implements EditService {


    @Autowired
    private FredhopperClient fredhopperClient;

    @Override
    public EditMenu getInContextMenuItems(Category category, MenuType menuType, Localization localization) {

        // TODO: Localize the menu items

        if ( menuType == MenuType.CREATE_NEW ) {

            String location = URLEncoder.encode(fredhopperClient.getLocation(category).toString());

            List<MenuItem> menuItems = new ArrayList<>();
            menuItems.add(new MenuItem("Promotion", "/fh-edit/campaigns.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
            menuItems.add(new MenuItem("Facet", "/fh-edit/facets.fh?id=_new&fh_location=" + location+ "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
            menuItems.add(new MenuItem("Modification", "/fh-edit/modified_results.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
            menuItems.add(new MenuItem("Ranking", "/fh-edit/rankings.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
            return new EditMenu("Create New...", menuItems);
        }
        return null;
    }
}

