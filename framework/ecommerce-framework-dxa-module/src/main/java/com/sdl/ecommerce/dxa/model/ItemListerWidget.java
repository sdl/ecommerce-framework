package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.Query;
import com.sdl.ecommerce.api.QueryInputContributor;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.webapp.common.api.mapping.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import java.util.List;

import static com.sdl.webapp.common.api.mapping.config.SemanticVocabulary.SDL_CORE;

/**
 * Item Lister Widget
 *
 * @author nic
 */
@SemanticEntity(entityName = "ItemListerWidget", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ItemListerWidget extends AbstractEntityModel implements QueryInputContributor {

    @SemanticProperty("e:category")
    private String category;

    @SemanticProperty("e:viewSize")
    private int viewSize = 0;

    // TODO: Add a semantic property
    private Boolean disableRedirect = null;

    private List<Product> items;

    private String nextUrl;
    private String previousUrl;
    private String lastUrl;
    private String firstUrl;
    private boolean showNavigation = false;
    private int currentSet;
    private int viewSets;

    @Override
    public void contributeToQuery(Query query) {
        if ( viewSize > 0 ) {
            query.viewSize(viewSize);
        }
        // TODO: HOW TO HANDLE FH SPECIFIC CONTRIBUTIONS?????
        /*
        if ( disableRedirect != null ) {
            queryConfiguration.setDisableRedirect(disableRedirect);
        } */
    }

    public String getCategory() {
        return category;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    public boolean isShowNavigation() {
        return showNavigation;
    }

    public void setShowNavigation(boolean showNavigation) {
        this.showNavigation = showNavigation;
    }

    public int getCurrentSet() {
        return currentSet;
    }

    public void setCurrentSet(int currentSet) {
        this.currentSet = currentSet;
    }

    public int getViewSets() {
        return viewSets;
    }

    public void setViewSets(int viewSets) {
        this.viewSets = viewSets;
    }
}
