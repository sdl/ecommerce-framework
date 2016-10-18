package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ProductRef;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

/**
 * ODataProductRef
 *
 * @author nic
 */
@EdmComplex(name = "ProductRef", namespace = "SDL.ECommerce")
public class ODataProductRef {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    public ODataProductRef() {}
    public ODataProductRef(ProductRef productRef) {
        this.id = productRef.getId();
        this.name = productRef.getName();
    }

    public ProductRef getReference() {
        return new ProductRef(this.id, this.name);
    }
}
