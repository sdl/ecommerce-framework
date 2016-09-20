package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.hybris.api.HybrisClientImpl;
import com.sdl.ecommerce.hybris.api.HybrisClientManager;
import com.sdl.ecommerce.hybris.model.HybrisCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Hybris Category Service
 *
 * @author nic
 */
@Component
public class HybrisCategoryService implements ProductCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(HybrisCategoryService.class);

    @Autowired
    private HybrisClientManager hybrisClientManager;

    @Autowired
    private LocalizationService localizationService;

    @Value("${hybris.categoryExpiryTimeout}")
    private long categoryExpiryTimeout = 60*1000*1000; // categories refreshed each 1 hour

    private Map<String, CategoryManager> categoryManagers = new HashMap<>();

    /**
     * Get category manager for current localization (site).
     * @return manager
     */
    private CategoryManager getCategoryManager() {

        String locale = localizationService.getLocale();
        CategoryManager categoryManager = categoryManagers.get(locale);
        if ( categoryManager == null ) {
            synchronized ( this ) {
                categoryManager = new CategoryManager(this.hybrisClientManager.getInstance(), this.categoryExpiryTimeout, this.getCatalogBranch());
                categoryManagers.put(locale, categoryManager);
            }
        }
        return categoryManager;
    }

    private String getCatalogBranch() {
        return this.localizationService.getLocalizedConfigProperty("hybris-catalogBranch");
    }

    @Override
    public Category getCategoryById(String id) throws ECommerceException {
        return this.getCategoryManager().getCategoryById(id);
    }

    @Override
    public Category getCategoryByPath(String path) throws ECommerceException {
        return this.getCategoryManager().getCategoryByPath(path);
    }

    @Override
    public List<Category> getTopLevelCategories() throws ECommerceException {
        return this.getCategoryManager().getTopLevelCategories();
    }
}
