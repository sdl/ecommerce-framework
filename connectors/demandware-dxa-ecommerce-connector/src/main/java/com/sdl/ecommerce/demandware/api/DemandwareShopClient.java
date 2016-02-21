package com.sdl.ecommerce.demandware.api;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.demandware.api.model.*;

import java.util.List;
import java.util.Map;

/**
 * Demandware Shop Client
 *
 * @author nic
 */
public interface DemandwareShopClient {

    /**
     * Create new basket
     * @return created basket
     * @throws ECommerceException
     */
    Basket createBasket() throws ECommerceException;

    /**
     * Add product to basket
     * @param basket
     * @param productId
     * @param quantity
     * @return updated basket
     * @throws ECommerceException
     */
    Basket addProductToBasket(Basket basket, String productId, int quantity) throws ECommerceException;

    /**
     * Add product item (product+quantity) to basket
     * @param basket
     * @param productItem
     * @return updated basket
     * @throws ECommerceException
     */
    Basket addProductItemToBasket(Basket basket, ProductItem productItem) throws ECommerceException;

    /**
     * Modify a product item in basket
     * @param basket
     * @param productItem
     * @return updated basket
     * @throws ECommerceException
     */
    Basket modifyProductItemInBasket(Basket basket, ProductItem productItem) throws ECommerceException;

    /**
     * Remove a product item from basket
     * @param basket
     * @param productItem
     * @return updated basket
     * @throws ECommerceException
     */
    Basket removeProductItemFromBasket(Basket basket, ProductItem productItem) throws ECommerceException;

    /**
     * Get product having specified identity/SKU
     * @param productId
     * @return product
     */
    Product getProduct(String productId);

    /**
     * Get category by ID.
     * @param categoryId
     * @return category
     */
    Category getCategory(String categoryId);

    /**
     * Get top level categories.
     * @param levels
     * @return list of categories
     */
    List<Category> getTopLevelCategories(int levels);

    /**
     * Get categories having specified category ID as parent
     * @param categoryId
     * @param levels can be 0-2
     * @return list of categories
     */
    List<Category> getCategories(String categoryId, int levels);

    /**
     * Search products by category
     * @param categoryId
     * @param count
     * @return search result
     */
    ProductSearchResult searchProductsByCategory(String categoryId, int count);

    /**
     * Search product by category and specified refinements (facets).
     * @param categoryId
     * @param count
     * @param refinements
     * @return search result
     */
    ProductSearchResult searchProductsByCategory(String categoryId, int count, Map<String,String> refinements);

    /**
     * Search products using specified category ID and/or search phrase.
     * @param searchPhrase
     * @param categoryId
     * @param start
     * @param count
     * @param refinements
     * @return search result
     */
    ProductSearchResult search(String searchPhrase, String categoryId, int start, int count, Map<String,String> refinements);

    /**
     * Get next result set.
     * @param searchResult
     * @return search result
     */
    ProductSearchResult getNext(ProductSearchResult searchResult);

    /**
     * Get previous result set.
     * @param searchResult
     * @return search result
     */
    ProductSearchResult getPrevious(ProductSearchResult searchResult);
}
