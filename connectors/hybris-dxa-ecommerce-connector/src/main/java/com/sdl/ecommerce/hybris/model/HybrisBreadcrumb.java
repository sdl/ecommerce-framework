package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.api.model.impl.GenericFacet;

/**
 * Hybris Breadcrumb
 *
 * @author nic
 */
public class HybrisBreadcrumb implements Breadcrumb {

    private com.sdl.ecommerce.hybris.api.model.Breadcrumb hybrisBreadcrumb;

    public HybrisBreadcrumb(com.sdl.ecommerce.hybris.api.model.Breadcrumb hybrisBreadcrumb) {
        this.hybrisBreadcrumb = hybrisBreadcrumb;
    }

    @Override
    public String getTitle() {
        return this.hybrisBreadcrumb.getFacetValueName();
    }

    @Override
    public boolean isCategory() {
        return false;
    }

    // TODO: REMOVE
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Category getCategory() {
        return null;
    }

    @Override
    public Facet getFacet() {
        return new GenericFacet(this.hybrisBreadcrumb.getFacetCode(), this.hybrisBreadcrumb.getFacetName(), this.hybrisBreadcrumb.getFacetValueCode(), Facet.FacetType.SINGLEVALUE);
    }
}
