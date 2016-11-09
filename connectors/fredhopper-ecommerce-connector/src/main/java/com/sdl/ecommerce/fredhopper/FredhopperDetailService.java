package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.sdl.ecommerce.fredhopper.FredhopperHelper.*;

/**
 * FredhopperDetailService
 *
 * @author nic
 */
@Component
public class FredhopperDetailService implements ProductDetailService {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private FredhopperClient fredhopperClient;

    @Autowired
    LocalizationService localizationService;

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {
        ProductDetailResult result = this.fredhopperClient.getDetail(productId,
                                                                     getUniverse(localizationService),
                                                                     getLocale(localizationService));
        this.injectServices(result);
        return result;
    }

    @Override
    public ProductDetailResult getDetail(String productId, Map<String, String> variantAttributes) throws ECommerceException {
        ProductDetailResult result = this.fredhopperClient.getDetail(productId,
                                                                     getUniverse(localizationService),
                                                                     getLocale(localizationService),
                                                                     variantAttributes);
        this.injectServices(result);
        return result;
    }

    private void injectServices(ProductDetailResult result) {
        FredhopperDetailResult fredhopperResult = (FredhopperDetailResult) result;
        fredhopperResult.setCategoryService(this.categoryService);
        fredhopperResult.setLocalizationService(this.localizationService);
    }
}
