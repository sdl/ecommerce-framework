package com.sdl.ecommerce.fredhopper.model.promotion;

import com.fredhopper.webservice.client.ContentAreaLink;
import com.fredhopper.webservice.client.Theme;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.ImageMapPromotion;
import com.sdl.ecommerce.api.model.Location;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Fredhopper ImageMap Promotion
 *
 * @author nic
 */
public class FredhopperImageMapPromotion extends FredhopperPromotion implements ImageMapPromotion {

    private String imageUrl;
    private List<ContentArea> contentAreas = new ArrayList<>();

    public FredhopperImageMapPromotion(Theme theme, FredhopperLinkManager linkManager, String editUrl, ProductCategoryService categoryService) {
        super(theme, editUrl);
        if ( theme.getStaticContent() != null && !theme.getStaticContent().getContent().isEmpty() ) {
            List<ContentAreaLink> areaLinks = theme.getStaticContent().getContent().get(0).getContentAreaLink();
            for ( ContentAreaLink areaLink : areaLinks ) {
                Location location = this.getPromotionLocation(areaLink, linkManager, categoryService);
                this.contentAreas.add(new ContentArea(areaLink.getX1(), areaLink.getY1(), areaLink.getX2(), areaLink.getY2(), location));
            }
            this.imageUrl =  linkManager.processImageUrl(theme.getStaticContent().getContent().get(0).getContentValue());
        }
    }

    @Override
    public List<ContentArea> getContentAreas() {
        return contentAreas;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getText() {
        return null;
    }
}
