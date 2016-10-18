package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.test.TestLinkResolver;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * TestContext
 *
 * @author nic
 */
@Configuration
public class TestContext {

    @Bean
    public DemandwareShopClientManager getShopClientManager() { return new DemandwareShopClientManager(); }

    @Bean
    public ProductCategoryService getCategoryService() { return new DemandwareCategoryService(); }

    @Bean
    public ProductQueryService getQueryService() { return new DemandwareQueryService(); }

    @Bean
    public ProductDetailService getDetailService() { return new DemandwareDetailService(); }

    @Bean
    public CartService getCartFactory() { return new DemandwareCartService(); }

    @Bean
    public LocalizationService getLocalizationService() { return new TestLocalizationService(); }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("application-test.properties"));
        placeholderConfigurer.setIgnoreResourceNotFound(true);
        return placeholderConfigurer;
    }

    @Bean
    public ECommerceLinkResolver getLinkResolver() { return new TestLinkResolver(); }

}
