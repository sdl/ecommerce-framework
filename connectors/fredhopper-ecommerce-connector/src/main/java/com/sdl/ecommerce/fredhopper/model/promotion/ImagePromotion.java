package com.sdl.ecommerce.fredhopper.model.promotion;

import com.fredhopper.webservice.client.Theme;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.ContentPromotion;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

/**
 * Image Promotion
 *
 * @author nic
 */
public class ImagePromotion extends FredhopperPromotion implements ContentPromotion {


    private String imageUrl;

    public ImagePromotion(Theme theme, FredhopperLinkManager linkManager, String editUrl, ProductCategoryService categoryService) {
        super(theme, editUrl);
        if ( theme.getStaticContent() != null && !theme.getStaticContent().getContent().isEmpty() ) {
            this.location = this.getPromotionLocation(theme.getStaticContent().getContent().get(0).getContentLink(), linkManager, categoryService);
            this.imageUrl = linkManager.processImageUrl(theme.getStaticContent().getContent().get(0).getContentValue());
        }
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
