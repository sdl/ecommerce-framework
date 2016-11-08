package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.odata.client.BasicODataClientQuery;
import com.sdl.odata.client.api.ODataClientQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * OData Category Manager
 * Manage all categories and cache them to minimize network load.
 *
 * @author nic
 */
public class ODataCategoryManager {

    private int categoryExpiryTimeout;
    private ODataCachedCategory rootCategory = new ODataCachedCategory();

    private ODataClient odataClient;

    /**
     * Constructor
     * @param oDataClient
     * @param categoryExpiryTimeout
     */
    public ODataCategoryManager(ODataClient oDataClient, int categoryExpiryTimeout) {
        this.odataClient = oDataClient;
        this.categoryExpiryTimeout = categoryExpiryTimeout;
        this.getTopLevelCategories();
    }

    /**
     * Get category by ID
     * @param id
     * @return category
     * @throws ECommerceException
     */
    public Category getCategoryById(String id) throws ECommerceException {

        // First recursively check in cache
        //
        Category category = getCategoryById(id, rootCategory.getCategories(), true);
        if ( category == null ) {
            // Secondly get the category and try to fit it into the cached structure
            //
            category = this.loadCategoryById(id);
            Category currentParent = rootCategory;
            List<String> parentIds = ((ODataCachedCategory)category).getParentIds();

            for ( String parentId : parentIds ) {
                Category parent = getCategoryById(parentId, currentParent.getCategories());
                if ( parent == null )
                {
                    // If something has changed with the category tree since last cache update
                    //
                    loadCategories(currentParent);
                    parent = getCategoryById(parentId, currentParent.getCategories());
                    if ( parent == null )
                    {
                        throw new ECommerceException("Inconsistent data returned from single category request and category tree requests.");
                    }
                }
                currentParent = parent;
                if ( parent.getCategories() == null )
                {
                    loadCategories(parent);
                }

                // If last item in the list -> Use that as parent reference for the current category
                //
                if (parentIds.indexOf(parentId) == parentIds.size() - 1)
                {
                    ((ODataCachedCategory)category).setParent(parent);
                    loadCategories(category);
                }
            }
        }
        return category;
    }

    /**
     * Get category by path
     * @param path
     * @return category
     * @throws ECommerceException
     */
    public Category getCategoryByPath(String path) throws ECommerceException {
        StringTokenizer tokens = new StringTokenizer(path, "/");
        Category category = null;
        while ( tokens.hasMoreTokens() ) {
            String pathName = tokens.nextToken();
            if ( category == null ) {
                category = getCategoryByPathName(rootCategory.getCategories(), pathName);
            }
            else {
                if ( ((ODataCachedCategory) category).needRefresh() ) {
                    loadCategories(category);
                }
                category = getCategoryByPathName(category.getCategories(), pathName);
            }
            if ( category == null ) {
                return null;
            }
        }
        if ( category != null && ((ODataCachedCategory) category).needRefresh() ) {
            loadCategories(category);
        }
        return category;
    }

    public List<Category> getTopLevelCategories() throws ECommerceException {
        if (rootCategory.needRefresh()) {
            loadCategories(rootCategory);
        }
        return rootCategory.getCategories();
    }

    /**
     * Load subordinated categories for specific category
     * @param parent
     * @throws ECommerceException
     */
    private void loadCategories(Category parent) throws ECommerceException {
        List<Category> categories;
        if ( parent == rootCategory ) {
            categories = this.loadTopLevelCategories();
        }
        else {
            categories = this.loadCategories(parent.getId());
        }
        List<Category> existingCategories = parent.getCategories();
        List<Category> newCategoryList = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            Category existingCategory = getCategory(category.getId(), existingCategories);
            if (existingCategory != null) {
                ((ODataCachedCategory)existingCategory).setParent(parent);
                newCategoryList.add(existingCategory);
            }
            else {
                ((ODataCachedCategory)category).setParent(parent);
                newCategoryList.add(category);
            }
        }

        synchronized (parent) {
            ((ODataCachedCategory) parent).setCategories(newCategoryList, System.currentTimeMillis() + categoryExpiryTimeout);
        }

    }

    /**
     * Load top level categories
     * @return categories
     * @throws ECommerceException
     */
    private List<Category> loadTopLevelCategories() throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCachedCategory.class)
                .build();
        List<Category> categories = (List<Category>) this.odataClient.getEntities(query);
        return categories;
    }

    /**
     * Load categories for specified parent category ID
     * @param parentCategoryId
     * @return categories
     * @throws ECommerceException
     */
    private List<Category> loadCategories(String parentCategoryId) throws ECommerceException {
        ODataClientQuery query = new ECommerceODataClientQuery.Builder()
                .withProperty("categories")
                .withEntityType(ODataCachedCategory.class)
                .withEntityKey("'" + parentCategoryId + "'")
                .build();
        return (List<Category>) this.odataClient.getEntities(query);
    }

    private Category loadCategoryById(String id) throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCachedCategory.class)
                .withEntityKey("'" + id + "'")
                .build();
        Category category = (Category) this.odataClient.getEntity(query);
        return category;
    }

    /**
     * Load categories by path
     * @param path
     * @return categories
     * @throws ECommerceException
     */
    private Category loadCategoryByPath(String path) throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCachedCategory.class)
                .withFilterMap("path", path)
                .build();
        List<Category> categories = (List<Category>) this.odataClient.getEntities(query);
        if ( categories.size() > 0 ) {
            Category category = categories.get(0);
            return category;
        }
        else {
            return null;
        }
    }

    private Category getCategoryById(String id, List<Category> categories) {
        return this.getCategoryById(id, categories, false);
    }

    /**
     * Get category by Id via the cache. If setting the optional parameter 'refresh=true', then checks
     * will be done on the categories while navigating if anyone needs to be refreshed.
     * @param id
     * @param categories
     * @param refresh
     * @return category
     */
    private Category getCategoryById(String id, List<Category> categories, boolean refresh) {
        if ( categories != null ) {
            for (Category category : categories) {
                if (category.getId().equals(id)) {
                    return category;
                }
                if (category.getCategories() != null)
                {
                    if ( refresh && ((ODataCachedCategory) category).needRefresh() )
                    {
                        loadCategories(category);
                    }
                    Category foundCategory = getCategoryById(id, category.getCategories());
                    if (foundCategory != null)
                    {
                        return foundCategory;
                    }
                }
            }
        }
        return null;
    }

    /**
     * et category by relative pathname
     * @param categories
     * @param pathName
     * @return category
     */
    private Category getCategoryByPathName(List<Category> categories, String pathName)
    {
        for (Category category : categories)
        {
            if (category.getPathName().equals(pathName))
            {
                return category;
            }
        }
        return null;
    }

    /**
     * Get category with specific ID from provided category list.
     * @param id
     * @param categories
     * @return category
     */
    private Category getCategory(String id, List<Category> categories) {
        if (categories != null)
        {
            for (Category category : categories) {
                if (category.getId().equals(id))
                {
                    return category;
                }
            }
        }
        return null;
    }
}
