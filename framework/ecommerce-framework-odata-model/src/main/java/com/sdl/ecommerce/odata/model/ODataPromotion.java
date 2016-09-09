package com.sdl.ecommerce.odata.model;

import com.sdl.ecommerce.api.model.ContentPromotion;
import com.sdl.ecommerce.api.model.ProductsPromotion;
import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.ecommerce.api.model.impl.GenericContentPromotion;
import com.sdl.ecommerce.api.model.impl.GenericImageMapPromotion;
import com.sdl.ecommerce.api.model.impl.GenericProductsPromotion;
import com.sdl.odata.api.edm.annotations.EdmComplex;
import com.sdl.odata.api.edm.annotations.EdmProperty;

import java.util.stream.Collectors;

/**
 * ODataPromotion
 *
 * @author nic
 */
@EdmComplex(name = "Promotion", namespace = "SDL.ECommerce")
public class ODataPromotion implements Promotion {

    @EdmProperty
    private String id;

    @EdmProperty
    private String name;

    @EdmProperty
    private String title;

    // Types of promotion
    //
    @EdmProperty
    private ODataProductsPromotion productsPromotion;

    @EdmProperty
    private ODataContentPromotion contentPromotion;


    public ODataPromotion() {}
    public ODataPromotion(Promotion promotion) {
        this.id = promotion.getId();
        this.name = promotion.getName();
        this.title = promotion.getTitle();
        if ( promotion instanceof ProductsPromotion ) {
            this.productsPromotion = new ODataProductsPromotion((ProductsPromotion) promotion);
        }
        else if ( promotion instanceof ContentPromotion) {
            this.contentPromotion = new ODataContentPromotion((ContentPromotion) promotion);
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public Promotion toPromotion() {
        if ( this.productsPromotion != null ) {
            return new GenericProductsPromotion(this.id,
                                                this.name,
                                                this.title,
                                                this.productsPromotion.getProducts());
        }
        else if ( this.contentPromotion != null && this.contentPromotion.getContentAreas() != null ) {
            return new GenericImageMapPromotion(this.id,
                                                this.name,
                                                this.title,
                                                this.contentPromotion.getImageUrl(),
                                                this.contentPromotion.getContentAreas().stream().
                                                        map(contentArea -> contentArea.toContentArea()).collect(Collectors.toList()));

        }
        else if ( this.contentPromotion != null ) {
            return new GenericContentPromotion(this.id,
                                               this.name,
                                               this.title,
                                               this.contentPromotion.getText(),
                                               this.contentPromotion.getImageUrl(),
                                               this.contentPromotion.getLocation());
        }
        return null;
    }
}
