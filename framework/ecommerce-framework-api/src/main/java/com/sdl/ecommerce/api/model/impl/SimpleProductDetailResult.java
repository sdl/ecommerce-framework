package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.Collections;
import java.util.List;

/**
 * SimpleProductDetailResult
 *
 * @author nic
 */
public class SimpleProductDetailResult implements ProductDetailResult {

    private Product product;

    public SimpleProductDetailResult(Product product) {
        this.product = product;
    }

    @Override
    public Product getProductDetail() {
        return this.product;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle) {
        return Collections.emptyList();
    }

    @Override
    public List<Promotion> getPromotions() {
        return Collections.emptyList();
    }
}
