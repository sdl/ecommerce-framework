package com.sdl.ecommerce.fredhopper.model.promotion;

import com.fredhopper.webservice.client.Theme;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductsPromotion;

import java.util.List;

/**
 * FredhopperProductsPromotion
 *
 * @author nic
 */
public class FredhopperProductsPromotion extends FredhopperPromotion implements ProductsPromotion {

    private List<Product> products;

    public FredhopperProductsPromotion(Theme theme, List<Product> products, String editUrl) {
        super(theme, editUrl);
        this.products = products;
    }

    @Override
    public List<Product> getProducts() {
        return this.products;
    }
}

