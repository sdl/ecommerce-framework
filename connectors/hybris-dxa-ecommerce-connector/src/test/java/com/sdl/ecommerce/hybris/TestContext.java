package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.test.TestLinkResolver;
import com.sdl.ecommerce.hybris.api.HybrisClientManager;
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
    public ProductCategoryService getProductCategoryService() {
        return new HybrisCategoryService();
    }

    @Bean
    public ProductQueryService getProductQueryService() { return new HybrisQueryService(); }

    @Bean
    public ProductDetailService getProductDetailService() { return new HybrisDetailService(); }

    @Bean
    public CartService getCartFactory() { return new HybrisCartService(); }

    @Bean
    public HybrisClientManager getHybrisClientManager() {
        return new HybrisClientManager();
    }

    @Bean
    public LocalizationService getLocalizationService() { return new TestLocalizationService(); }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("application-test.properties"));
        return placeholderConfigurer;
    }

    @Bean
    public ECommerceLinkResolver getLinkResolver() { return new TestLinkResolver(); }
}
