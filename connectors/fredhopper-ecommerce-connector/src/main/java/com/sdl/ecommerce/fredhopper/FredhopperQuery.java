package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.ViewType;
import com.sdl.ecommerce.api.Query;

import java.util.Map;

/**
 * Query Configuration
 *
 * @author nic
 */
public class FredhopperQuery extends Query {

    private Boolean forcedNaturalSorting;
    private Boolean disableRedirect;
    private Map<String, Integer> facetLimits;
    private ViewType pageType;
    private Boolean showStoreLink;

    static final int DEFAULT_VIEW_SIZE = 20;

    public FredhopperQuery() {
        this.viewSize(DEFAULT_VIEW_SIZE);
    }
    // TODO: Convert to chained interface here!!

    public Boolean isDisableRedirect() {
        return disableRedirect;
    }

    public void setDisableRedirect(Boolean disableRedirect) {
        this.disableRedirect = disableRedirect;
    }

    public Map<String, Integer> getFacetLimits() {
        return facetLimits;
    }

    public void setFacetLimits(Map<String, Integer> facetLimits) {
        this.facetLimits = facetLimits;
    }

    public Boolean isForcedNaturalSorting() {
        return forcedNaturalSorting;
    }

    public void setForcedNaturalSorting(Boolean forcedNaturalSorting) {
        this.forcedNaturalSorting = forcedNaturalSorting;
    }

    public Boolean isShowStoreLink() {
        return showStoreLink;
    }

    public void setShowStoreLink(Boolean showStoreLink) {
        this.showStoreLink = showStoreLink;
    }

    public ViewType getPageType() {
        return pageType;
    }

    public void setPageType(ViewType pageType) {
        this.pageType = pageType;
    }
}
