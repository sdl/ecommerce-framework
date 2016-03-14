package com.sdl.ecommerce.dxa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MVC Configurer.
 * Sets up additional configuration such as interceptors etc.
 *
 * @author nic
 */
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PreviewDetectionInterceptor());
    }
}
