package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * Category
 *
 * @author nic
 */
public interface Category {

    String getId();

    String getName();

    Category getParent();

    List<Category> getCategories();

    String getCategoryLink(String urlPrefix); // TODO: How to handle the url prefix????? Managed the caller itself????

    String getPathName();
}
