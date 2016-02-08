package com.sdl.ecommerce.dxa;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sdl.ecommerce.api.model.Category;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * CategoryDataCache
 *
 * @author nic
 */
@Component
public class CategoryDataCache {

    private Cache<String, Object> categoryData = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.MINUTES).build();

    public Object getCategoryData(Category category, String key) {
        return this.categoryData.getIfPresent(category.getId() + ":" + key);
    }

    public void setCategoryData(Category category, String key, Object categoryData) {
        this.categoryData.put(category.getId() + ":" + key, categoryData);
    }

}
