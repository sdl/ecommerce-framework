package com.sdl.ecommerce.hybris.api;


import com.sdl.ecommerce.hybris.TestContext;
import com.sdl.ecommerce.hybris.api.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestHybrisClient
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class TestHybrisClient {

    @Autowired
    private HybrisClient client;


    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> allCategories = client.getAllCategories();
        printCategories(allCategories,0);

    }

    private void printCategories(List<Category> categories, int level) {
        for ( Category category : categories ) {
            for ( int i=0; i < level; i++ ) {
                System.out.print("-");
            }
            System.out.println(" " + category.getName() + " (" + category.getId() + ")");
            printCategories(category.getSubcategories(), level + 1);
        }
    }

    @Test
    public void testGetCategory() throws Exception {

        Category category = client.getCategory("576");
        System.out.println("Category " + category.getId() + ", " + category.getName());
        System.out.println("Products:");
        for ( Product product : category.getProducts() ) {
            System.out.println(" " + product.getManufacturer() + " " + product.getName());
        }
    }

    @Test
    public void testSearch() throws Exception {
        SearchResult result = client.search("sony", 20, 0);
        System.out.println("Products:");
        for ( Product product : result.getProducts() ) {
            System.out.println(" " + product.getManufacturer() + " " + product.getName());
        }
        System.out.println("Facets:");
        for ( Facet facet : result.getFacets() ) {
            System.out.println("------ Facet name: " + facet.getName());
            for ( FacetValue facetValue : facet.getValues() ) {
                System.out.println(facetValue.getName() + " (" + facetValue.getCount() + ")");
            }
        }
    }

    @Test
    public void testSearchWithFacets() throws Exception {

        List<FacetPair> facets = new ArrayList<>();
        facets.add(new FacetPair("price", "$500-$999.99"));
        SearchResult result = client.search("sony", 20, 0, null, facets);
        System.out.println("Products:");
        for ( Product product : result.getProducts() ) {
            System.out.println(" " + product.getManufacturer() + " " + product.getName());
        }
        System.out.println("Facets:");
        for ( Facet facet : result.getFacets() ) {
            System.out.println("------ Facet name: " + facet.getName() + " ID: " + facet.getId());
            for ( FacetValue facetValue : facet.getValues() ) {
                System.out.println(facetValue.getName() + "(ID: " + facetValue.getId() + ") (" + facetValue.getCount() + ")" + (facetValue.isSelected() ? " SELECTED" : ""));
            }
        }
    }

    @Test
    public void testGetProduct() throws Exception {

        Product product = client.getProduct("676442");
        System.out.println("Product " + product.getName() + ", " + product.getManufacturer());

        product = client.getProduct("816324");
        System.out.println("Product " + product.getName() + ", " + product.getManufacturer());
        System.out.println("Stock level: " + product.getStock().getStockLevel());
    }

    @Test
    public void testCart() throws Exception {

        String sessionId = client.createCart();
        System.out.println("Created cart with session ID: " + sessionId);
        Cart cart = client.getCart(sessionId);
        System.out.println("Cart total items: " + cart.getTotalItems());
        System.out.println("Adding product to cart...");
        cart = client.addItemToCart(sessionId, "676442", 1);
        System.out.println("Cart total items: " + cart.getTotalItems() + ", total price: " + cart.getTotalPrice().getFormattedValue());
        cart = client.addItemToCart(sessionId, "149243", 1);
        System.out.println("Cart total items: " + cart.getTotalItems() + ", total price: " + cart.getTotalPrice().getFormattedValue());
    }

}
