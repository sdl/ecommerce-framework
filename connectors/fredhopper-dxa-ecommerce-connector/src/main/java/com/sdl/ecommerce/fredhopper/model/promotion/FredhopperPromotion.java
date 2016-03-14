package com.sdl.ecommerce.fredhopper.model.promotion;

import com.fredhopper.webservice.client.ContentLinkBase;
import com.fredhopper.webservice.client.Theme;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Editable;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.Promotion;
import com.sdl.ecommerce.fredhopper.FredhopperLinkManager;

import java.util.List;
import java.util.StringTokenizer;

/**
 * FredhopperPromotion
 *
 * @author nic
 */
public class FredhopperPromotion implements Promotion, Editable {


    protected String id;
    protected String name;
    protected String title;
    protected String slogan;
    protected String link;
    protected String editUrl;

    protected FredhopperPromotion(Theme theme, String editUrl) {
        this.id = theme.getId();
        this.name = theme.getName();
        this.title= theme.getTitle();
        this.slogan = theme.getSlogan();
        this.editUrl = editUrl;
    }

    public static Promotion build(Theme theme, FredhopperLinkManager linkManager, List<Product> products, String editUrl, ProductCategoryService categoryService) {
        if ( theme.getStaticContent() != null ) {
            String type =  theme.getStaticContent().getType().value();
            if ( type.equals("image") ) {
                return new ImagePromotion(theme, linkManager, editUrl, categoryService);
            }
            else if (type.equals("image-map") ) {
                return new FredhopperImageMapPromotion(theme, linkManager, editUrl, categoryService);
            }
            else if ( type.equals("text") ) {
                return new TextPromotion(theme, linkManager, editUrl, categoryService);
            }
        }
        else {
            // Product promotion
            //
            return new FredhopperProductsPromotion(theme, products, editUrl);
        }
        return null;
    }

    protected String getPromotionLink(ContentLinkBase contentLink, FredhopperLinkManager linkManager, ProductCategoryService categoryService) {

        if ( contentLink.getType().value().equals("catalog") ) {
            StringTokenizer tokenizer = new StringTokenizer(contentLink.getValue(), "=&");
            while ( tokenizer.hasMoreTokens() ) {
                String token = tokenizer.nextToken();
                if ( token.equals("fh_location") ) {
                    String locationLink = tokenizer.nextToken();
                    return linkManager.convertToSEOLink(locationLink, categoryService);
                }
            }
            return null;

        }
        else {
            return contentLink.getValue();
        }


    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    //@Override
    public String getSlogan() {
        return slogan;
    }

    //@Override
    public String getLink() {
        return link;
    }

    @Override
    public String getEditUrl() {
        return editUrl;
    }
}

