package com.sdl.ecommerce.odata.client;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.test.GenericTestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * ODataClientIT
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class ODataClientIT extends GenericTestSuite {

    private static final Logger LOG = LoggerFactory.getLogger(ODataClientIT.class);

    @Test
    public void testGetCategoryById() throws Exception {
        this.testGetCategoryById("catalog01_18661_18682");
    }

    @Test
    public void testGetCategoryByPath() throws Exception {
        this.testGetCategoryByPath("/women");
    }

    @Test
    public void testGetTopLevelCategories() throws Exception {
        List<Category> categories = this.categoryService.getTopLevelCategories();
        LOG.info("Top level categories:");
        for ( Category category : categories ) {
            LOG.info("Category: " + category.getName() + " (" + category.getId() + ")");
        }
    }

    @Test
    public void testGetProductDetail() throws Exception {
        this.testGetProductDetail("008010231960");
    }

    @Test
    public void testListProductsInCategory() throws Exception {
        this.testGetProductsInCategory("/women");
    }

    @Test
    public void testProductSearch() throws Exception {
        this.testSearch("watch", "/c");
    }

    @Test
    public void testProductSearchWithFacets() throws Exception {
        List<FacetParameter> facets = new ArrayList<>();
        facets.add(new FacetParameter("brand", "dkny"));
        this.testSearchWithFacets("women watch", facets);
    }

    @Test
    public void testPromotions() throws Exception {
        this.testPromotions("/women");
    }

    @Test
    public void testCart() throws Exception {
        this.testCart("008010231960");
    }

}
