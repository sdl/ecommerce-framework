package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.odata.model.NavigationPropertyResolver;
import com.sdl.ecommerce.odata.model.ODataCategory;
import com.sdl.odata.client.BasicODataClientQuery;
import com.sdl.odata.client.api.ODataClientQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * OData Category Service
 *
 * @author nic
 */
@Component
public class ODataCategoryService implements ProductCategoryService, NavigationPropertyResolver {


    @Autowired
    private ODataClient odataClient;

    @PostConstruct
    public void initialize() {
        this.odataClient.registerModelClass(ODataCategory.class);
    }

    @Override
    public Category getCategoryById(String id) throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCategory.class)
                .withEntityKey("'" + id + "'")
                .build();
        Category category = (Category) this.odataClient.getEntity(query);
        processCategory(category);
        return category;
    }

    @Override
    public Category getCategoryByPath(String path) throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCategory.class)
                .withFilterMap("path", path)
                .build();
        List<Category> categories = (List<Category>) this.odataClient.getEntities(query);
        if ( categories.size() > 0 ) {
            Category category = categories.get(0);
            processCategory(category);
            return category;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Category> getTopLevelCategories() throws ECommerceException {
        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCategory.class)
                .build();
        List<Category> categories = (List<Category>) this.odataClient.getEntities(query);
        categories.forEach( (category) -> processCategory(category));
        return categories;
    }

    @Override
    public Object resolve(String key, String propertyName) {
        if ( propertyName.equals("categories") ) {
            return this.getCategories(key);
        }
        return null;
    }

    public List<ODataCategory> getCategories(String parentCategoryId) {
        ODataClientQuery query = new ECommerceODataClientQuery.Builder()
                .withProperty("categories")
                .withEntityType(ODataCategory.class)
                .withEntityKey("'" + parentCategoryId + "'")
                .build();
        List<ODataCategory> categories = (List<ODataCategory>) this.odataClient.getEntities(query);
        categories.forEach( (category) -> processCategory(category));
        return categories;
    }

    private void processCategory(Category category) {
        ((ODataCategory) category).setNavigationPropertyResolver(this);
    }

}
