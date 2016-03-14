package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.hybris.api.HybrisClient;
import com.sdl.ecommerce.hybris.model.HybrisCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Category Manager
 *
 * @author nic
 */
public class CategoryManager {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryManager.class);

    private HybrisClient hybrisClient;
    private Category rootCategory;
    private long categoryTreeExpiryTime = 0;
    private long categoryExpiryTimeout;
    private String catalogBranch = null;

    // TODO: Make a generic category manager base class

    /**
     * Create new category manager for a specific localization.
     * @param hybrisClient
     * @param categoryExpiryTimeout
     * @param catalogBranch
     */
    public CategoryManager(HybrisClient hybrisClient, long categoryExpiryTimeout, String catalogBranch) {
        this.hybrisClient = hybrisClient;
        this.categoryExpiryTimeout = categoryExpiryTimeout;
        this.catalogBranch = catalogBranch;
        this.loadAllCategories();
    }

    public Category getCategoryById(String id) throws ECommerceException {
        checkCategoryExpiryTime();
        return this.getCategoryById(id, this.rootCategory.getCategories());
    }

    private Category getCategoryById(String id, List<Category> categories) {
        for ( Category category : categories ) {
            if ( category.getId().equals(id) ) {
                return category;
            }
            if ( category.getCategories() != null ) {
                Category foundCategory = this.getCategoryById(id, category.getCategories());
                if (foundCategory != null) {
                    return foundCategory;
                }
            }
        }
        return null;
    }

    public Category getCategoryByPath(String path) throws ECommerceException {
        checkCategoryExpiryTime();
        LOG.debug("Getting category by path: " + path);
        return this.getCategoryByPath(path, this.rootCategory.getCategories());
    }

    private Category getCategoryByPath(String path, List<Category> topCategories) throws ECommerceException {
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        Category category = null;
        while ( tokenizer.hasMoreTokens() ) {
            String pathName = tokenizer.nextToken();
            if ( category == null ) {
                category = getCategoryByPathName(topCategories, pathName);
            }
            else {
                category = getCategoryByPathName(category.getCategories(), pathName);
            }
            if ( category == null ) {
                return null;
            }
        }
        return category;
    }

    private Category getCategoryByPathName(List<Category> categories, String pathName) {
        for ( Category category : categories ) {
            if ( category.getPathName().equals(pathName) ) {
                return category;
            }
        }
        return null;
    }

    private void checkCategoryExpiryTime() {
        if ( this.categoryTreeExpiryTime < System.currentTimeMillis() ) {
            loadAllCategories();
        }
    }

    /**
     * Load all categories from Hybris
     */
    private synchronized void loadAllCategories() {
        Category topCategory = new HybrisCategory(this.hybrisClient.getAllCategories());
        if ( this.catalogBranch != null ) {
            this.rootCategory = this.getCategoryByPath(this.catalogBranch, topCategory.getCategories());
        }
        else {
            this.rootCategory = topCategory;
        }
        // Exclude the root catalog from the tree navigation
        //
        for ( Category category : this.rootCategory.getCategories() ) {
            ((HybrisCategory) category).setParent(null);
        }
        categoryTreeExpiryTime = System.currentTimeMillis() + categoryExpiryTimeout;
    }

}
