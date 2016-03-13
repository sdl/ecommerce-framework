package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import static com.sdl.ecommerce.fredhopper.FredhopperHelper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Fredhopper Category Service
 *
 * @author nic
 */
@Component
public class FredhopperCategoryService implements ProductCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FredhopperCategoryService.class);

    @Autowired
    private FredhopperClient fredhopperClient;

    @Autowired
    private LocalizationService localizationService;

    @Value("${fredhopper.categoryExpiryTimeout:3600000}")
    private long categoryExpiryTimeout;

    private Map<String, CategoryManager> categoryManagers = new HashMap<>();

    /**
     * Get category manager for current localization. Each localization has its own category structure.
     *
     * @return category manager
     */
    private CategoryManager getCategoryManager() {

        String publicationId = this.localizationService.getPublicationId();
        CategoryManager categoryManager = categoryManagers.get(publicationId);
        if ( categoryManager == null ) {
            synchronized ( this ) {
                 categoryManager = new CategoryManager(this.fredhopperClient,
                                                       this.categoryExpiryTimeout,
                                                       getUniverse(localizationService),
                                                       getLocale(localizationService));
                categoryManagers.put(publicationId, categoryManager);
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

}
