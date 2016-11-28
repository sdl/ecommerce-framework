package com.sdl.ecommerce.fredhopper.admin;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.edit.EditMenu;
import com.sdl.ecommerce.api.edit.EditService;
import com.sdl.ecommerce.api.edit.MenuItem;
import com.sdl.ecommerce.api.edit.impl.EditMenuImpl;
import com.sdl.ecommerce.api.edit.impl.MenuItemImpl;
import com.sdl.ecommerce.fredhopper.FredhopperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.sdl.ecommerce.fredhopper.FredhopperHelper.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Fredhopper Edit Service
 * Get links to create new facets, promotions etc.
 *
 * @author nic
 */
@Component
public class FredhopperEditService implements EditService {

    private static final Logger LOG = LoggerFactory.getLogger(FredhopperEditService.class);

    @Autowired
    private FredhopperClient fredhopperClient;

    @Autowired
    private LocalizationService localizationService;

    @Override
    public EditMenu getInContextMenuItems(Query query) {

        String location = fredhopperClient.getLocation(query.getCategory(), query.getSearchPhrase(), getUniverse(localizationService), getLocale(localizationService)).toString();
        try {
            location = URLEncoder.encode(location, "UTF-8");
        }
        catch ( UnsupportedEncodingException e ) {
            LOG.error("Could not URL encode Fredhopper location: " + location, e);
        }

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItemImpl("Promotion", buildFredhopperEditUrl("campaigns.fh", location)));
        menuItems.add(new MenuItemImpl("Facet", buildFredhopperEditUrl("facets.fh", location)));
        menuItems.add(new MenuItemImpl("Modification", buildFredhopperEditUrl("modified_results.fh", location)));
        menuItems.add(new MenuItemImpl("Ranking", buildFredhopperEditUrl("rankings.fh", location)));
        if ( query.getSearchPhrase() != null ) {
            menuItems.add(new MenuItemImpl("Synonyms", buildFredhopperEditUrl("search_synonyms.fh", location)));
        }
        menuItems.add(new MenuItemImpl("Redirect", buildFredhopperEditUrl("search_redirects.fh", location)));
        return new EditMenuImpl("Create New...", menuItems);
    }

    private String buildFredhopperEditUrl(String editPage, String location) {
        return "/fh-edit/" + editPage + "?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories";
    }
}

