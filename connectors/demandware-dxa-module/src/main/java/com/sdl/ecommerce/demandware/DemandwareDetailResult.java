package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;

import java.util.ArrayList;
import java.util.List;

/**
 * DemandwareDetailResult
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
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {
        // TODO: This code is identical with the one in the Fredhopper version
        // -> Do a common base class using default implementation of the interfaces
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        if ( this.productDetail.getCategories() != null && this.productDetail.getCategories().size() > 0 ) {
            Category category = this.productDetail.getCategories().get(0);
            while ( category != null ) {
                breadcrumbs.add(0, new GenericBreadcrumb(category.getName(), category.getCategoryLink(urlPrefix), true));
                category = category.getParent();
            }
        }
        if ( rootTitle != null ) {
            breadcrumbs.add(0, new GenericBreadcrumb(rootTitle, urlPrefix, true));
        }
        return breadcrumbs;
    }

    @Override
    public List<Promotion> getPromotions() {
        // TODO: TO BE IMPLEMENTED !!!
        return null;
    }
}
