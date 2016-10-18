package com.sdl.ecommerce.api.model.impl;

import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductsPromotion;

import java.util.List;

/**
 * Generic ProductsPromotion implementation
 *
 * @author nic
 */
public class GenericProductsPromotion extends GenericPromotion implements ProductsPromotion {

    private List<Product> products;

    public GenericProductsPromotion(String id, String name, String title, List<Product> products) {
        super(id, name, title);
        this.products = products;
    }

    public GenericProductsPromotion(String id, String name, String title, String editUrl, List<Product> products) {
        super(id, name, title, editUrl);
        this.products = products;
    }

    @Override
    public List<Product> getProducts() {
        return this.products;
    }
}
