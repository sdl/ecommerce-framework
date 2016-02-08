package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Override
    public ProductDetailResult getDetail(String productId) throws ECommerceException {
        ProductDetailResult result = this.fredhopperClient.getDetail(productId);
        this.injectServices(result);
        return result;
    }

    private void injectServices(ProductDetailResult result) {
        FredhopperDetailResult fredhopperResult = (FredhopperDetailResult) result;
        fredhopperResult.setCategoryService(this.categoryService);
    }
}
