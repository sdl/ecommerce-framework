package com.sdl.ecommerce.api.model;

/**
 * CategoryRef
 *
 * @author nic
 */
public class CategoryRef {

    private String id;
    private String name;
    private String path;

    public CategoryRef(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.path = getCategoryAbsolutePath(category);
    }

    public CategoryRef(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public static String getCategoryAbsolutePath(Category category) {
        String path = "";
        Category currentCategory = category;
        while ( currentCategory != null && currentCategory.getPathName() != null ) {
            path = currentCategory.getPathName() + "/" + path;
            currentCategory = currentCategory.getParent();
        }
        return "/" + path;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
