package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

/**
 * TestHybrisServices
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class TestHybrisServices {

    private static final Logger LOG = LoggerFactory.getLogger(TestHybrisServices.class);

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductQueryService queryService;

    @Autowired
    private ProductDetailService detailService;

    @Test
    public void testGetCategoryById() throws Exception {

        Category category = this.categoryService.getCategoryById("571");
        LOG.info("Category ID: " + category.getId() + ", Name: " + category.getName());
        printCategories(category.getCategories());
    }

    @Test
    public void testGetCategoryByPath() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/cameras/film_cameras");
        LOG.info("Category ID: " + category.getId() + ", Name: " + category.getName());
        printCategories(category.getCategories());
    }

    @Test
    public void testGetProductsInCategory() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/cameras");
        QueryResult result = this.queryService.query(this.queryService.newQuery().category(category));
        this.printProducts(result.getProducts());
        result = result.next();
        LOG.info("Next 20 products:");
        this.printProducts(result.getProducts());
    }

    @Test
    public void testSearch() throws Exception {
        QueryResult result = this.queryService.query(this.queryService.newQuery().searchPhrase("sony").viewSize(10));
        LOG.info("Total count: " + result.getTotalCount());
        this.printProducts(result.getProducts());
        result = result.next();
        this.printFacets(result.getFacetGroups(null));
        LOG.info("Next 10 products:");
        this.printProducts(result.getProducts());
    }

    @Test
    public void testSearchWithFacets() throws Exception {
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        searchPhrase("sony").
                        viewSize(10).
                        facet(new FacetParameter("Color", "Black")));
        LOG.info("Total count: " + result.getTotalCount());
        this.printProducts(result.getProducts());
    }

    @Test
    public void testGetProductDetail() throws Exception {
        LOG.info("Getting detail for product...");
        ProductDetailResult result = this.detailService.getDetail("478828");
        Product product = result.getProductDetail();
        LOG.info("Product ID: " + product.getId());
        LOG.info("Product Name: " + product.getName());
        LOG.info("Product Description: " + product.getDescription());
        LOG.info("Price: " + product.getPrice().getPrice());
        LOG.info("Detail Page URL:" + product.getDetailPageUrl());
        LOG.info("Primary Image URL: " + product.getPrimaryImageUrl());
        LOG.info("Categories: ");
        for ( Category category : product.getCategories() ) {
            LOG.info("ID: " + category.getId() + " Name: " + category.getName() + " Parent: " + category.getParent().getName());
        }
        /*
        LOG.info("Breadcrumbs: ");
        this.printBreadcrumbs(result.getBreadcrumbs("/products", "Products"));
        this.printPromotions(result.getPromotions());
        LOG.info("Facets: ");
        for ( FacetParameter facet : product.getFacets() ) {
            LOG.info(facet.getName() + " : " + facet.getValues());
        }
        */
        LOG.info("Attributes: ");
        for ( String attrName : product.getAttributes().keySet() ) {
            LOG.info("Name: " + attrName + " Value: " + product.getAttributes().get(attrName));
        }
    }

    /******** SUPPORT PRINTOUT FUNCTIONS ***********/

    private void printCategories(List<Category> categories) {
        LOG.info("------ Categories: ---------");
        for ( Category subCategory : categories ) {
            LOG.info("Category ID: " + subCategory.getId() + ", Name: " + subCategory.getName());
        }
    }

    private void printProducts(List<Product> products) {
        LOG.info("------ Products: --------");
        for ( Product product : products ) {
            LOG.info("Product ID: " + product.getId() + " Name: " + product.getName() + " Thumbnail: " + product.getThumbnailUrl());
        }
    }

    private void printFacets(List<FacetGroup> facetGroups) {
        LOG.info("Facets:");
        LOG.info("###############");
        for ( FacetGroup facetGroup : facetGroups ) {
            LOG.info("Facet group title: " + facetGroup.getTitle() + ", type:" + facetGroup.getType());
            LOG.info("--------------------------------------------");
            for ( Facet facet : facetGroup.getFacets() ) {
                LOG.info(facet.getTitle() + " (" + facet.getCount() + ")" + " URL: " + facet.getUrl());
            }
            LOG.info("");
        }
    }

    private void printBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        LOG.info("Breadcrumbs:");
        for ( Breadcrumb breadcrumb : breadcrumbs ) {
            LOG.info("\"" + breadcrumb.getTitle() + "\"" + " URL: " + breadcrumb.getUrl() + " category: " + breadcrumb.isCategory());
        }
    }

}
