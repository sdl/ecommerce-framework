package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.Query;
import com.fredhopper.webservice.client.Page;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.fredhopper.model.FredhopperCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Category Manager
 * Manage categories for a specific locale/universe
 *
 * @author nic
 */
public class CategoryManager {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryManager.class);

    private FredhopperClient fredhopperClient;
    private long categoryExpiryTimeout;
    private String universe;
    private String locale;

    private Category rootCategory = new FredhopperCategory(null, null, null);

    public CategoryManager(FredhopperClient fredhopperClient, long categoryExpiryTimeout, String universe, String locale) {
        this.fredhopperClient = fredhopperClient;
        this.categoryExpiryTimeout = categoryExpiryTimeout;
        this.universe = universe;
        this.locale = locale;
        this.readTopLevelCategories();
    }

    /**
     * Get category by specified ID
     * @param id
     * @return category
     * @throws ECommerceException
     */
    public Category getCategoryById(String id) throws ECommerceException {
        return this.getCategoryById(id, this.getTopLevelCategories());
    }

    private Category getCategoryById(String id, List<Category> categories) {
        Category foundCategory = null;
        for ( Category category : categories ) {
            if ( category.getId().equals(id) ) {
                foundCategory = category;
                break;
            }
            if ( ((FredhopperCategory)category).needsRefresh() && id.startsWith(category.getId()) ) {
                this.loadCategories(category);
            }
            if ( category.getCategories() != null ) {
                Category subCategory = this.getCategoryById(id, category.getCategories());
                if (subCategory != null) {
                    foundCategory = subCategory;
                    break;
                }
            }
        }
        if ( foundCategory != null && ((FredhopperCategory) foundCategory).needsRefresh() ) {
            this.loadCategories(foundCategory);
        }
        return foundCategory;
    }

    /**
     * Get category by specified path
     * @param path
     * @return category
     * @throws ECommerceException
     */
    public Category getCategoryByPath(String path)  throws ECommerceException {

        LOG.debug("Getting category by path: " + path);
        StringTokenizer tokenizer = new StringTokenizer(path, "/");
        Category category = null;
        while ( tokenizer.hasMoreTokens() ) {
            String pathName = tokenizer.nextToken();
            if ( category == null ) {
                category = getCategoryByPathName(this.getTopLevelCategories(), pathName);
            }
            else {
                if ( ((FredhopperCategory)category).needsRefresh() ) {
                    this.loadCategories(category);
                }
                category = getCategoryByPathName(category.getCategories(), pathName);
            }
            if ( category == null ) {
                return null;
            }
        }
        if ( category != null && ((FredhopperCategory)category).needsRefresh() ) {
            this.loadCategories(category);
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

    public List<Category> getTopLevelCategories() {
        if ( ((FredhopperCategory)this.rootCategory).needsRefresh() ) {
            loadCategories(this.rootCategory);
        }
        return this.rootCategory.getCategories();
    }

    /**
     * Read top level categories
     */
    private void readTopLevelCategories() {

        LOG.debug("Reading top level categories...");
        Query query = this.fredhopperClient.buildQuery(universe, locale);
        Page page = this.fredhopperClient.doQuery(query);
        List<Category> topLevelCategories = this.fredhopperClient.getCategories(null, fredhopperClient.getUniverse(page));
        ((FredhopperCategory) this.rootCategory).setCategories(topLevelCategories, System.currentTimeMillis() + categoryExpiryTimeout);
        for ( Category category : topLevelCategories ) {
            loadCategories(category);
        }
    }

    /**
     * Load categories belonging to provided parent category.
     *
     * @param parent
     */
    private void loadCategories(Category parent) {

        LOG.debug("Loading sub-categories for category: " + (parent == this.rootCategory ? "ROOT" : parent.getName()));
        Query query = this.fredhopperClient.buildQuery(universe, locale);
        if ( parent != this.rootCategory ) {
            this.fredhopperClient.buildCategoryQuery(query, parent);
        }
        Page page = this.fredhopperClient.doQuery(query);
        List<Category> categories = this.fredhopperClient.getCategories(parent, this.fredhopperClient.getUniverse(page));
        LOG.debug("Got " + categories.size() + " categories");
        List<Category> existingCategories = parent.getCategories();

        // Verify so we did not get the same categories as parent structure.
        // This happens if the category facet is set to multi-select
        //
        for ( Category category : categories ) {
            if ( category.getId().equals(parent.getId()) ) {
                categories = new ArrayList<>();
                break;
            }
        }
        //
        if ( existingCategories != null ) {
            // If a refresh -> rebuild the list and reuse items
            //
            synchronized ( parent ) {
                for ( int i=0; i < categories.size(); i++ ) {
                    Category category = categories.get(i);
                    Category existingCategory = this.getCategory(category.getId(), existingCategories);
                    if ( existingCategory != null ) {
                        categories.add(i, existingCategory);
                        categories.remove(i+1);
                    }
                }
            }
        }
        ((FredhopperCategory) parent).setCategories(categories, System.currentTimeMillis() + categoryExpiryTimeout);
    }

    private Category getCategory(String id, List<Category> categories) {
        for ( Category category : categories ) {
            if ( category.getId().equals(id) ) {
                return category;
            }
        }
        return null;
    }
}
