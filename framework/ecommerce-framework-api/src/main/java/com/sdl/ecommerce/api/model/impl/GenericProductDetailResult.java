package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.Collections;
import java.util.List;

/**
 * Generic Product Detail Result
 *
 * @author nic
 */
public class GenericProductDetailResult implements ProductDetailResult {

    private Product product;
    private List<Breadcrumb> breadcrumbs = Collections.emptyList();
    private List<Promotion> promotions = Collections.emptyList();

    public GenericProductDetailResult(Product product) {
        this.product = product;
    }

    public GenericProductDetailResult(Product product, List<Breadcrumb> breadcrumbs, List<Promotion> promotions) {
        this.product = product;
        this.breadcrumbs = breadcrumbs;
        this.promotions = promotions;
    }

    @Override
    public Product getProductDetail() {
        return this.product;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        return this.breadcrumbs;
    }

    @Override
    public List<Promotion> getPromotions() {
        return this.promotions;
    }
}
