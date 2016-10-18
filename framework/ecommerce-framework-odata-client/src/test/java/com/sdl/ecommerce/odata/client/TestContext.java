package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.test.TestLinkResolver;
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

    @Bean
    public ECommerceLinkResolver getLinkResolver() { return new TestLinkResolver(); }
}
