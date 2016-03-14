package com.sdl.ecommerce.fredhopper.model.promotion;

import com.fredhopper.webservice.client.Theme;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.ContentPromotion;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

/**
 * Text Promotion
 *
 * @author nic
 */
public class TextPromotion extends FredhopperPromotion implements ContentPromotion {

    private String line1;
    private String line2;

    public TextPromotion(Theme theme, FredhopperLinkManager linkManager, String editUrl, ProductCategoryService categoryService) {
        super(theme, editUrl);
        if ( theme.getStaticContent() != null && !theme.getStaticContent().getContent().isEmpty() ) {
            this.link = this.getPromotionLink(theme.getStaticContent().getContent().get(0).getContentLink(), linkManager, categoryService);
            this.title = theme.getStaticContent().getContent().get(0).getContentValue();
            this.line1 = theme.getStaticContent().getContent().get(1).getContentValue();
            this.line2 = theme.getStaticContent().getContent().get(2).getContentValue();
        }
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public String getText() {
        return line1 + "\n" + line2;
    }
}
