package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductLinkStrategy;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default Product Link Strategy
 *
 * @author nic
 */
@Component
public class DefaultProductLinkStrategy implements ProductLinkStrategy {

    @Autowired
    private LocalizationService localizationService;

    @Override
    public String getCategoryLink(Category category, String urlPrefix) {

        String url = "";
        Category currentCategory = category;
        while ( currentCategory != null ) {
            url = currentCategory.getPathName() + "/" + url;
            currentCategory = currentCategory.getParent();
        }
        url = urlPrefix + "/" + url;
        return url;
    }

    @Override
    public String getProductDetailLink(Product product) {

        String name = product.getName();
        if ( name != null ) {
            // Generate a SEO friendly URL
            //
            String seoName = product.getName().toLowerCase().replace(" ", "-").replace("'", "").replace("--", "");
            return this.localizationService.localizePath("/p/") + seoName + "/" + product.getId();
        }
        return this.localizationService.localizePath("/p/") + product.getId();
    }
}
