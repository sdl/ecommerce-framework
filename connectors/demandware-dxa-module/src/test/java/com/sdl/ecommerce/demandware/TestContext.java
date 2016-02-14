package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
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
    public DemandwareShopClient getShopClient() { return new DemandwareShopClient(); }

    @Bean
    public ProductCategoryService getCategoryService() { return new DemandwareCategoryService(); }

    @Bean
    public ProductQueryService getQueryService() { return new DemandwareQueryService(); }

    @Bean
    public ProductDetailService getDetailService() { return new DemandwareDetailService(); }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("application-test.properties"));
        return placeholderConfigurer;
    }
}
