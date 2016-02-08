package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.hybris.api.HybrisClient;
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
    public HybrisClient getHybrisClient() {
        return new HybrisClient();
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfig() {
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("application-test.properties"));
        return placeholderConfigurer;
    }
}
