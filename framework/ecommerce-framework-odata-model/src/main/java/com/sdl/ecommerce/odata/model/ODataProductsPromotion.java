package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductsPromotion;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OData Products Promotion
 *
 * @author nic
 */
@EdmComplex(name = "ProductsPromotion", namespace = "SDL.ECommerce")
public class ODataProductsPromotion {

    @EdmProperty
    private List<ODataProductSummary> products = new ArrayList<>();

    public ODataProductsPromotion() {}
    public ODataProductsPromotion(ProductsPromotion productsPromotion) {
        productsPromotion.getProducts().forEach(product -> products.add(new ODataProductSummary(product)));
    }

    public List<Product> getProducts() {
        return this.products.stream().collect(Collectors.toList());
    }

}
