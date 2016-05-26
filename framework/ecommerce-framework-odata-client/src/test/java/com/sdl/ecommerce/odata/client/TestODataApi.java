package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.odata.model.ODataCategory;
import com.sdl.odata.client.*;
import com.sdl.odata.client.api.ODataClientComponentsProvider;
import com.sdl.odata.client.api.ODataClientQuery;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * TestODataApi
 *
 * @author nic
 */
public class TestODataApi {

    private static final Logger LOG = LoggerFactory.getLogger(TestODataApi.class);

    @Test
    public void testGetCategory() throws Exception {

        LOG.info("Getting category from OData service...");

        DefaultODataClient client = new DefaultODataClient();
        ODataClientComponentsProvider provider = new ODataV4ClientComponentsProvider(Arrays.asList(ODataCategory.class.getName()),
                new ClientPropertiesBuilder().withServiceUri("http://localhost:8080/ecommerce.svc").build());
        client.configure(provider);

        ODataClientQuery query = new BasicODataClientQuery.Builder()
                .withEntityType(ODataCategory.class)
                .withEntityKey("'catalog01_18661'")
                .build();

        LOG.info("Query: " + query.getQuery());

        Category category = (Category) client.getEntity(new HashMap<String,String>(), query);
        LOG.info("Category: " + category.getId() + " name: " + category.getName());

        ODataClientQuery query2 = new ECommerceODataClientQuery.Builder()
                .withProperty("categories")
                .withEntityType(ODataCategory.class)
                .withEntityKey("'catalog01_18661'")
                .build();

        client.encodeURL(false);
        List<?> categories = client.getEntities(new HashMap<String,String>(), query2);
        LOG.info("No of categories: " + categories.size());
        LOG.info("Subcategories:");
        for ( Object subCategoryObj : categories ) {
            Category subCategory = (Category) subCategoryObj; // Ugly as hell...
            LOG.info("Subcategory: " + subCategory.getId() + " name: " + subCategory.getName());
        }
    }
}
