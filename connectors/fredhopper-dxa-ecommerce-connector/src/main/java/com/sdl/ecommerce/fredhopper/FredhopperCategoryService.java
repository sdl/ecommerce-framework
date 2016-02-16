package com.sdl.ecommerce.fredhopper;

import com.fredhopper.lang.query.Query;
import com.fredhopper.webservice.client.Page;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.fredhopper.model.FredhopperCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.StringTokenizer;

/**
 * FredhopperCategoryService
 *
 * @author nic
 */
@Component
public class FredhopperCategoryService implements ProductCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(FredhopperCategoryService.class);

    // TODO: Have some kind of generic base class for these kind of services???

    @Autowired
    private FredhopperClient fredhopperClient;

    private Category rootCategory = new FredhopperCategory(null, null, null);

    static final long CATEGORY_EXPIRY_TIMEOUT = 60*1000*1000; // categories refreshed each 1 hour

    @PostConstruct
    public void initialize() {
        this.readTopLevelCategories();
    }

    @Override
    public Category getCategoryById(String id) throws ECommerceException {
        return this.getCategoryById(id, this.getTopLevelCategories());
    }

    private Category getCategoryById(String id, List<Category> categories) {
        for ( Category category : categories ) {
            if ( category.getId().equals(id) ) {
                return category;
            }
            if ( ((FredhopperCategory)category).needsRefresh() && id.startsWith(category.getId()) ) {
                this.loadCategories(category);
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

    @Override
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

    private List<Category> getTopLevelCategories() {
        if ( ((FredhopperCategory)this.rootCategory).needsRefresh() ) {
            loadCategories(this.rootCategory);
        }
        return this.rootCategory.getCategories();
    }

    private void readTopLevelCategories() {

        Query query = this.fredhopperClient.buildQuery();
        Page page = this.fredhopperClient.doQuery(query);
        List<Category> topLevelCategories = this.fredhopperClient.getCategories(null, fredhopperClient.getUniverse(page));
        ((FredhopperCategory) this.rootCategory).setCategories(topLevelCategories, System.currentTimeMillis() + CATEGORY_EXPIRY_TIMEOUT);
        for ( Category category : topLevelCategories ) {
            loadCategories(category);
        }
    }

    private void loadCategories(Category parent) {

        LOG.debug("Loading sub-categories for category: " + (parent == this.rootCategory ? "ROOT" : parent.getName()));
        Query query = this.fredhopperClient.buildQuery();
        if ( parent != this.rootCategory ) {
            this.fredhopperClient.buildCategoryQuery(query, parent);
        }
        Page page = this.fredhopperClient.doQuery(query);
        List<Category> categories = this.fredhopperClient.getCategories(parent, this.fredhopperClient.getUniverse(page));
        List<Category> existingCategories = parent.getCategories();

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
        ((FredhopperCategory) parent).setCategories(categories, System.currentTimeMillis() + CATEGORY_EXPIRY_TIMEOUT);
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
