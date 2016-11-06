package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ECommerceLinkResolver;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;

import java.util.ArrayList;
import java.util.List;

/**
 * Demandware Detail Result
 *
 * @author nic
 */
public class DemandwareDetailResult implements ProductDetailResult {

    private Product productDetail;

    public DemandwareDetailResult(Product productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public Product getProductDetail() {
        return this.productDetail;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        if ( this.productDetail.getCategories() != null && this.productDetail.getCategories().size() > 0 ) {
            Category category = this.productDetail.getCategories().get(0);
            while ( category != null ) {
                breadcrumbs.add(0, new GenericBreadcrumb(category.getName(), new CategoryRef(category)));
                category = category.getParent();
            }
        }
        return breadcrumbs;
    }

    @Override
    public List<Promotion> getPromotions() {
        // TODO: TO BE IMPLEMENTED !!!
        return new ArrayList<>();
    }
}
