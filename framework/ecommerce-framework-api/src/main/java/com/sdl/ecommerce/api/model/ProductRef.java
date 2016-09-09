package com.sdl.ecommerce.api.model;

/**
 * ProductRef
 *
 * @author nic
 */
public class ProductRef {

    private String id;
    private String name;

    public ProductRef(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    public ProductRef(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
