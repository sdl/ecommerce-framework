package com.sdl.webapp.navigation.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import java.util.ArrayList;
import java.util.List;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Navigation Section
 *
 * @author nic
 */
@SemanticEntity(entityName = "NavigationSection", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class NavigationSection extends AbstractEntityModel {

    @SemanticProperty("e:mainItem")
    private NavigationItem mainItem;

    @SemanticProperty("e:items")
    private List<NavigationItem> items;

    // TODO: Have this configurable in metadata
    private int cols = 4;

    public NavigationItem getMainItem() {
        return mainItem;
    }

    public List<NavigationItem> getItems() {
        return items;
    }

    public List<List<NavigationItem>> getItemColumns() {
        List<List<NavigationItem>> columns = new ArrayList<>();
        if ( items != null ) {
            List<NavigationItem> column = new ArrayList<>();
            columns.add(column);
            int i = 0;
            for (NavigationItem item : items) {
                column.add(item);
                i++;
                if (i % cols == 0) {
                    column = new ArrayList<>();
                    columns.add(column);
                }
            }
        }
        return columns;
    }
}
