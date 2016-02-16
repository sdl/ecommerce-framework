package com.sdl.ecommerce.demandware.api;

import com.sdl.ecommerce.demandware.TestContext;
import com.sdl.ecommerce.demandware.api.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test Demandware Shop
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class TestDemandwareShop {

    static private Log log = LogFactory.getLog(TestDemandwareShop.class);

    @Autowired
    private DemandwareShopClient shopClient;

    @Test
    public void testAddToCart() throws Exception {

        Basket basket = this.shopClient.createBasket();

        log.info("Adding product to cart...");

        basket = this.shopClient.addProductToBasket(basket, "sanyo-dp50747", 1);
        this.printBasket(basket);
    }

    @Test
    public void testGetTopCategories() throws Exception {
        log.info("Getting top level categories...");

        List<Category> allCategories = this.shopClient.getTopLevelCategories(2);
        printCategories(allCategories, "-");
    }

    @Test
    public void testGetProduct() throws Exception {
        log.info("Test getting product...");

        Product product = this.shopClient.getProduct("sanyo-dp50747");
        log.info("Product ID: " + product.getId() + " name: " + product.getName());
        log.info("Short description: " + product.getShort_description());
        for ( ImageGroup imageGroup : product.getImage_groups() ) {
            log.info("Image Group view type: " + imageGroup.getView_type());
            for ( Image image : imageGroup.getImages() ) {
                log.info("Image link: " + image.getLink());
            }
        }

    }

    @Test
    public void testGetProductsByCategory() throws Exception {

        log.info("Getting category info...");
        Category category = this.shopClient.getCategory("electronics-televisions");//"electronics-televisions"); // "electronics-digital-cameras"
        log.info("Category name: " + category.getName());
        log.info("Getting products by category...");

        ProductSearchResult searchResult = this.shopClient.searchProductsByCategory(category.getId(), 16);
        log.info("Number of products: " + searchResult.getTotal());

        log.info("Available Refinements:");
        for ( ProductSearchRefinement refinement : searchResult.getRefinements() ) {
            log.info("Refinement ID:" + refinement.getAttribute_id() + ", label: " + refinement.getLabel());
        }

        while ( searchResult != null ) {
            for (ProductSearchHit hit : searchResult.getHits()) {
                log.info("  Product ID: " + hit.getProduct_id());
            }
            log.info("Getting next products...");
            searchResult = this.shopClient.getNext(searchResult);
        }

    }

    @Test
    public void testGetProductsByRefinements() throws Exception {

        log.info("Getting getting products by refinements...");
        Category category = this.shopClient.getCategory("electronics-televisions");
        log.info("Category name: " + category.getName());
        log.info("Getting products by category...");

        Map<String,String> refinements = new HashMap<>();
        refinements.put("brand", "Sony");
        ProductSearchResult searchResult = this.shopClient.searchProductsByCategory(category.getId(), 16, refinements);

        log.info("Number of products: " + searchResult.getTotal());

        for (ProductSearchHit hit : searchResult.getHits()) {
            log.info("  Product ID: " + hit.getProduct_id());
        }

        log.info("Selected refinements: " + searchResult.getSelected_refinements());
        Map<String,String> refinements2 = searchResult.getSelected_refinements();
        //refinements2.put("brand", "Samsung|Sony");
        refinements2.put("price", "(500..1000)");
        log.info("Updated refinements: " + refinements2);

        searchResult = this.shopClient.searchProductsByCategory(category.getId(), 16, refinements2);

        log.info("Number of products: " + searchResult.getTotal());

        for (ProductSearchHit hit : searchResult.getHits()) {
            log.info("  Product ID: " + hit.getProduct_id());
        }
        log.info("Selected refinements: " + searchResult.getSelected_refinements());

    }

    @Test
    public void testSearch() throws Exception {

        ProductSearchResult result = this.shopClient.search("sony", null, 0, 20, null);
        for ( ProductSearchHit hit : result.getHits() ) {
            log.info("Product: " + hit.getProduct_id());
        }
    }

    private void printBasket(Basket basket) {
        log.info("Basket ID: " + basket.getBasket_id());
        log.info("Basket currency:" + basket.getCurrency() + " product total: " + basket.getProduct_total() + " subtotal: " + basket.getProduct_sub_total());
        log.info("ETag: " + basket.getEtag());
        if ( basket.getProduct_items() != null ) {
            log.info("Products: ");
            for (ProductItem productItem :basket.getProduct_items()) {
                log.info("Product ID: " + productItem.getProduct_id() + ",Name: " + productItem.getProduct_name() + ", Text: " + productItem.getItem_text());
            }
            log.info("Total price: " + basket.getProduct_total());
        }
    }

    private void printCategories(List<Category> categories, String intend) {
        for ( Category category : categories ) {
            log.info(intend + " " + category.getName() + " (" + category.getId() + ")");
            if ( category.getCategories() != null ) {
                printCategories(category.getCategories(), intend + "-");
            }
        }
    }
}
