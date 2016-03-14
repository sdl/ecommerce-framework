package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.Facet;

/**
 * Hybris Facet
 *
 * @author nic
 */
public class HybrisFacet implements Facet {

    // TODO: Have some kind of default implementations of the different E-Com APIs???

    private String title;
    private String url;
    private int count;
    private boolean isSelected;

    public HybrisFacet(String title, String url, int count, boolean isSelected) {
        this.title = title;
        this.url = url;
        this.count = count;
        this.isSelected = isSelected;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }
}
