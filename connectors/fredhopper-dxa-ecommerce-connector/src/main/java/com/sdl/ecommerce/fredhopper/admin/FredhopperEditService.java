package com.sdl.ecommerce.fredhopper.admin;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.edit.EditMenu;
import com.sdl.ecommerce.api.edit.EditService;
import com.sdl.ecommerce.api.edit.MenuItem;
import com.sdl.ecommerce.fredhopper.FredhopperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.sdl.ecommerce.fredhopper.FredhopperHelper.*;

import java.io.UnsupportedEncodingException;
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
        menuItems.add(new MenuItem("Promotion", "/fh-edit/campaigns.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        menuItems.add(new MenuItem("Facet", "/fh-edit/facets.fh?id=_new&fh_location=" + location+ "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        menuItems.add(new MenuItem("Modification", "/fh-edit/modified_results.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        menuItems.add(new MenuItem("Ranking", "/fh-edit/rankings.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        if ( query.getSearchPhrase() != null ) {
            menuItems.add(new MenuItem("Synonyms", "/fh-edit/search_synonyms.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        }
        menuItems.add(new MenuItem("Redirect", "/fh-edit/search_redirects.fh?id=_new&fh_location=" + location + "&fh_location_selection_mode=exact_path&fh_reffacet=categories"));
        return new EditMenu("Create New...", menuItems);


    }
}

