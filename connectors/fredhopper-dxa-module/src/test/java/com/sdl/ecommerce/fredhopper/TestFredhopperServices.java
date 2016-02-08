package com.sdl.ecommerce.fredhopper;

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
 * TestFredhopperServices
 *
 * @author nic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContext.class, loader = AnnotationConfigContextLoader.class)
public class TestFredhopperServices {

    private static final Logger LOG = LoggerFactory.getLogger(TestFredhopperServices.class);

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductQueryService queryService;

    @Autowired
    private ProductDetailService detailService;

    // TODO: Add asserts here!!

    @Test
    public void testGetCategory() throws Exception {

        Category category = this.categoryService.getCategoryByPath("/women");
        LOG.info("Category ID: " + category.getId() + ", Name: " + category.getName());

        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10));
        this.printProducts(result);
        LOG.info("Next set of products =>");
        result = result.next();
        this.printProducts(result);
        LOG.info("Previous set of products =>");
        result = result.previous();
        this.printProducts(result);
    }

    @Test
    public void testNavigateCategoryTree() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/women");
        LOG.info("Navigate through subcategories:");
        for( Category subCategory : category.getCategories() ) {
            LOG.info("Category:" + subCategory.getName());
            QueryResult result = this.queryService.query(
                                            this.queryService.newQuery().category(subCategory));
            this.printProducts(result);
        }
    }

    @Test
    public void testPromotions() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/women");
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10));
        this.printPromotions(result.getPromotions());
    }

    @Test
    public void testFacets() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/women");
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10));
        this.printFacets(result.getFacetGroups(null));
    }

    @Test
    public void testBreadcrumbs() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/women/accessories/watches");
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10));
        this.printBreadcrumbs(result.getBreadcrumbs("/products", "Products"));
    }

    @Test
    public void testSearch() throws Exception {
        QueryResult result = this.queryService.query(this.queryService.newQuery().searchPhrase("women watch").viewSize(100));
        LOG.info("Total count: " + result.getTotalCount());
        this.printProducts(result);
        this.printFacets(result.getFacetGroups(null));
    }

    @Test
    public void testQueryFlyout() throws Exception {
        Category category = this.categoryService.getCategoryByPath("/women");
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10).
                        viewType(ViewType.FLYOUT));

        this.printFacets(result.getFacetGroups(null));
        this.printPromotions(result.getPromotions());
    }

    @Test
    public void testGetProductDetail() throws Exception {
        LOG.info("Getting detail for product...");
        ProductDetailResult result = this.detailService.getDetail("008010231960");
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
        LOG.info("Breadcrumbs: ");
        this.printBreadcrumbs(result.getBreadcrumbs("/products", "Products"));
        this.printPromotions(result.getPromotions());
        LOG.info("Facets: ");
        for ( FacetParameter facet : product.getFacets() ) {
            LOG.info(facet.getName() + " : " + facet.getValues());
        }
        LOG.info("Attributes: ");
        for ( String attrName : product.getAttributes().keySet() ) {
            LOG.info("Name: " + attrName + " Value: " + product.getAttributes().get(attrName));
        }
    }

    @Test
    public void testQueryWithRedirect() throws Exception {
        LOG.info("Querying a category & facet that only gives one product item...");
        Category category = this.categoryService.getCategoryByPath("/women/accessories");
        QueryResult result = this.queryService.query(
                this.queryService.newQuery().
                        category(category).
                        viewSize(10).
                        facet(new FacetParameter("brand", "adidas")).
                        facet(new FacetParameter("spotlight", "this20week27s20brochure")));
        LOG.info("Redirect URL: " + result.getRedirectUrl());
    }

    @Test
    public void testSearchRedirect() throws Exception {
        LOG.info("Testing redirects that are triggered by specific search keywords...");
        QueryResult result = this.queryService.query(this.queryService.newQuery().searchPhrase("contact").viewSize(100));
        LOG.info("Redirect URL: " + result.getRedirectUrl());
    }


    /******** SUPPORT PRINTOUT FUNCTIONS ***********/

    private void printProducts(QueryResult resultSet) {
        LOG.info("Products:");
        for ( Product product : resultSet.getProducts() ) {
            LOG.info("  Product ID: " + product.getId() + " Name: " + product.getName());
        }
    }

    private void printPromotions(List<Promotion> promotions) {
        LOG.info("Promotions:");
        for ( Promotion promotion : promotions ) {
            LOG.info("Promo ID: " + promotion.getId() + " Name: "  + promotion.getName() + " Title: " + promotion.getTitle());
        }
    }

    private void printFacets(List<FacetGroup> facetGroups) {
        LOG.info("Facets:");
        LOG.info("###############");
        for ( FacetGroup facetGroup : facetGroups ) {
            LOG.info("Facet group title: " + facetGroup.getTitle() + ", type:" + facetGroup.getType());
            LOG.info("--------------------------------------------");
            for ( Facet facet : facetGroup.getFacets() ) {
                LOG.info(facet.getTitle() + " (" + facet.getCount() + ")");
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
