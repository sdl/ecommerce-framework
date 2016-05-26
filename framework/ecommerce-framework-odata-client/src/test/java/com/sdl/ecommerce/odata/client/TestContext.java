package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TestContext
 *
 * @author nic
 */
@Configuration
public class TestContext {

    @Bean
    public ProductCategoryService getProductCategoryService() {
        return new ODataCategoryService();
    }

    @Bean
    public ProductDetailService getProductDetailService() { return new ODataProductDetailService(); }

    @Bean
    public ProductQueryService getProductQueryService() { return new ODataProductQueryService(); }

    @Bean
    public ODataClient getODataClient() {
        return new ODataClient();
    }

    @Bean
    public LocalizationService getLocalizationService() { return new TestLocalizationService(); }
}
