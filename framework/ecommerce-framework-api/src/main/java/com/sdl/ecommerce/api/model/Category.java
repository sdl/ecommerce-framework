package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * E-Commerce Category
 *
 * @author nic
 */
public interface Category {

    /**
     * Get category ID.
     * @return id
     */
    String getId();

    /**
     * Get name of the category. Should be localized to current language.
     * @return name
     */
    String getName();

    /**
     * Get parent category. Returns NULL if root category.
     * @return parent
     */
    Category getParent();

    /**
     * Get all sub-categories.
     * @return list of categories
     */
    List<Category> getCategories();

    /**
     * Get SEO friendly link to the category.
     * @param urlPrefix
     * @return link
     */
    String getCategoryLink(String urlPrefix);

    /**
     * Get the category's path name which can based on the name of the category.
     * @return path name
     */
    String getPathName();
}
