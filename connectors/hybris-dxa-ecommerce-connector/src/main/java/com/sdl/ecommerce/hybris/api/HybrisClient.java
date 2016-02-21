package com.sdl.ecommerce.hybris.api;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.hybris.api.model.*;

import java.util.List;

/**
 * Hybris Client
 *
 * @author nic
 */
public interface HybrisClient {

    /**
     * @return all categories
     */
    List<Category> getAllCategories();

    /**
     * Get category with specified ID.
     * @param categoryId
     * @return category
     */
    Category getCategory(String categoryId);

    /**
     * Search products.
     * @param searchPhrase
     * @param pageSize
     * @param currentPage
     * @return search result
     */
    SearchResult search(String searchPhrase, int pageSize, int currentPage);

    /**
     * Search products using facets.
     * @param searchPhrase
     * @param pageSize
     * @param currentPage
     * @param sort
     * @param facets
     * @return search result
     */
    SearchResult search(String searchPhrase, int pageSize, int currentPage, String sort, List<FacetPair> facets);

    /**
     * Get product with specified identity/SKU.
     * @param productId
     * @return product
     */
    Product getProduct(String productId);

    /**
     * Create new cart.
     * @return cart
     */
    String createCart();

    /**
     * Get cart
     * @param sessionID
     * @return cart
     */
    Cart getCart(String sessionID);

    /**
     * Add item to the cart.
     * @param sessionID
     * @param productId
     * @param amount
     * @return updated cart
     * @throws ECommerceException
     */
    Cart addItemToCart(String sessionID, String productId, int amount) throws ECommerceException;

    /**
     * Remove item from the cart.
     * @param sessionID
     * @param productId
     * @return updated cart
     * @throws ECommerceException
     */
    Cart removeItemFromCart(String sessionID, String productId) throws ECommerceException;

    /**
     * Modify an item in the cart.
     * @param sessionID
     * @param productId
     * @param amount
     * @return updated cart
     * @throws ECommerceException
     */
    Cart modifyItemInCart(String sessionID, String productId,
                          int amount) throws ECommerceException;


}
