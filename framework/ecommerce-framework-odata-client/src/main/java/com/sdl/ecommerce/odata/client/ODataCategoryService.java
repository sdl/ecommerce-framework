package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OData Category Service
 *
 * @author nic
 */
@Component
public class ODataCategoryService implements ProductCategoryService {

    @Autowired
    private ODataClient odataClient;

    @Autowired
    private LocalizationService localizationService;

    @Value("${ecommerce.odata.categoryExpiryTimeout:3600000}")
    private int categoryExpiryTimeout = 3600000;

    private Map<String, ODataCategoryManager> categoryManagers = new HashMap<>();

    @PostConstruct
    public void initialize() {
        this.odataClient.registerModelClass(ODataCachedCategory.class);
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

    private ODataCategoryManager getCategoryManager() {
        String locale = this.localizationService.getLocale();
        ODataCategoryManager categoryManager = this.categoryManagers.get(locale);
        if ( categoryManager == null ) {
            categoryManager = new ODataCategoryManager(this.odataClient, this.categoryExpiryTimeout);
            this.categoryManagers.put(locale, categoryManager);
        }
        return categoryManager;
    }

}
