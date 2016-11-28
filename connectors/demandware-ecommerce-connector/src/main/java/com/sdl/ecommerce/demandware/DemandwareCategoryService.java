package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientImpl;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
import com.sdl.ecommerce.demandware.model.DemandwareCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Demandware Category Service
 *
 * @author nic
 */
@Component
public class DemandwareCategoryService implements ProductCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(DemandwareCategoryService.class);

    @Autowired
    private DemandwareShopClientManager shopClientManager;

    @Autowired
    private LocalizationService localizationService;

    @Value("${demandware.categoryExpiryTimeout:3600000}")
    private long categoryExpiryTimeout;

    private Map<String, CategoryManager> categoryManagers = new HashMap<>();

    /**
     * Get category manager for current localisation (site).
     *
     * @return category manager
     */
    private CategoryManager getCategoryManager() {


        String locale = localizationService.getLocale();
        CategoryManager categoryManager = categoryManagers.get(locale);
        if ( categoryManager == null ) {
            synchronized ( this ) {
                categoryManager = new CategoryManager(this.shopClientManager.getInstance(), this.categoryExpiryTimeout);
                categoryManagers.put(locale, categoryManager);
            }
        }
        return categoryManager;
    }

    @Override
    public Category getCategoryById(String id) throws ECommerceException {
        return this.getCategoryManager().getCategoryById(id);
    }

    @Override
    public Category getCategoryByPath(String path)  throws ECommerceException {
        return this.getCategoryManager().getCategoryByPath(path);
    }

    @Override
    public List<Category> getTopLevelCategories() throws ECommerceException {
        return this.getCategoryManager().getTopLevelCategories();
    }
}
