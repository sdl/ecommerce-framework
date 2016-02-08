package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * HybrisCategory
 *
 * @author nic
 */
public class HybrisCategory implements Category {

    private com.sdl.ecommerce.hybris.api.model.Category hybrisCategory;
    private Category parent = null;
    private List<Category> categories = null;

    public HybrisCategory(List<com.sdl.ecommerce.hybris.api.model.Category> categories) {
        this.categories = new ArrayList<>();
        for (com.sdl.ecommerce.hybris.api.model.Category hybrisCategory : categories ) {
            this.categories.add(new HybrisCategory(this, hybrisCategory));
        }
    }

    public HybrisCategory(Category parent, com.sdl.ecommerce.hybris.api.model.Category hybrisCategory) {
        this.hybrisCategory = hybrisCategory;
        this.parent = parent;
    }

    @Override
    public List<Category> getCategories() {
        if ( categories == null ) {
            synchronized ( this ) {
                if ( categories == null ) {
                    categories = new ArrayList<>();
                    List<com.sdl.ecommerce.hybris.api.model.Category> hybrisSubcategories = this.hybrisCategory.getSubcategories();
                    for (com.sdl.ecommerce.hybris.api.model.Category hybrisSubcategory : hybrisSubcategories ) {
                        categories.add(new HybrisCategory(this, hybrisSubcategory));
                    }
                }
            }
        }
        return this.categories;
    }

    @Override
    public String getId() {
        return this.hybrisCategory.getId();
    }

    @Override
    public String getName() {
        return this.hybrisCategory.getName();
    }

    @Override
    public Category getParent() {
        return this.parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public String getPathName() {
        return this.hybrisCategory.getName().toLowerCase().replace(" ", "_").replace("&", "and");
    }

    @Override
    public String getCategoryLink(String urlPrefix) {
        String link = "";
        Category currentCategory = this;
        while ( currentCategory != null ) {
            link = currentCategory.getPathName() + "/" + link;
            currentCategory = currentCategory.getParent();
        }
        link = urlPrefix + "/" + link;
        return link;
    }
}
