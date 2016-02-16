package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
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
        return new FredhopperCategoryService();
    }

    @Bean
    public ProductQueryService getProductQueryService() {
        return new FredhopperQueryService();
    }

    @Bean
    public ProductDetailService getProductDetailService() {
        return new FredhopperDetailService();
    }

    @Bean
    public FredhopperClient getFredhopperClient() {
        return new FredhopperClient();
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("application-test.properties"));
        return placeholderConfigurer;
    }
}
