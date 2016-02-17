package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.model.*;
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
 * Test Demandware Services
 *
 * The tests are based on the standard Demandware demo shop (Genesis)
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class DemandwareConnectorIT extends GenericTestSuite {

    private static final Logger LOG = LoggerFactory.getLogger(DemandwareConnectorIT.class);

    @Test
    public void testGetCategoryById() throws Exception {

        this.testGetCategoryById("womens");
    }

    @Test
    public void testGetCategoryByPath() throws Exception {
        this.testGetCategoryByPath("/womens/footwear");
    }

    @Test
    public void testGetProductsInCategory() throws Exception {
        this.testGetProductsInCategory("/womens/footwear");
    }

    @Test
    public void testGetDeepCategory() throws Exception {
        this.testGetCategoryByPath("/electronics/televisions/flat_screen");
    }

    @Test
    public void testSearch() throws Exception {
        this.testSearch("sony");
    }

    @Test
    public void testSearchWithFacets() throws Exception {

        List<FacetParameter> facets = new ArrayList<>();
        facets.add(new FacetParameter("brand", "Sony|Samsung"));
        this.testSearchWithFacets("tv", facets);
    }

    @Test
    public void testGetProductDetail() throws Exception {
        this.testGetProductDetail("sanyo-dp50747");
    }

    @Test
    public void testCart() throws Exception {
        this.testCart("sanyo-dp50747", "sanyo-dp50747");
    }

}
