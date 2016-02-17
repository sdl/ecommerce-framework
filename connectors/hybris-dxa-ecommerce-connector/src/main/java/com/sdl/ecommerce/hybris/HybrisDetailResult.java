package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.ecommerce.hybris.model.HybrisBreadcrumb;

import java.util.ArrayList;
import java.util.List;

/**
 * Hybris Detail Result
 *
 * @author nic
 */
public class HybrisDetailResult implements ProductDetailResult {

    private Product productDetail;

    public HybrisDetailResult(Product productDetail) {
        this.productDetail = productDetail;

    }

    @Override
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {

        // TODO: This code is identical with the one in the Fredhopper version
        // -> Do a common base class using default implementation of the interfaces
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        if ( this.productDetail.getCategories() != null ) {
            for ( Category category : this.productDetail.getCategories() ) {
                breadcrumbs.add(0, new HybrisBreadcrumb(category.getName(), category.getCategoryLink(urlPrefix), true));
            }
        }
        if ( rootTitle != null ) {
            breadcrumbs.add(0, new HybrisBreadcrumb(rootTitle, urlPrefix, true));
        }
        return breadcrumbs;
    }

    @Override
    public Product getProductDetail() {
        return this.productDetail;
    }

    @Override
    public List<Promotion> getPromotions() {
        return new ArrayList<>();
    }
}
