package com.sdl.ecommerce.demandware.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.api.model.impl.GenericCartItem;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientImpl;
import com.sdl.ecommerce.demandware.api.model.Basket;
import com.sdl.ecommerce.demandware.api.model.ProductItem;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DemandwareCart
 *
 * @author nic
 */
public class DemandwareCart implements Cart {

    private DemandwareShopClient shopClient;
    private ProductDetailService detailService;
    private List<CartItem> items = new ArrayList<>();
    private int count = 0;
    private ProductPrice totalPrice = null;
    private Basket basket = null;

    public DemandwareCart(DemandwareShopClient shopClient, ProductDetailService detailService) {
        this.shopClient = shopClient;
        this.detailService = detailService;
        this.basket = this.shopClient.createBasket();
        this.refresh();
    }

    @Override
    public String getId() {
        return this.basket != null ? this.basket.getBasket_id() : null;
    }

    @Override
    public void addProduct(String productId) throws ECommerceException {
        this.addProduct(productId, 1);
    }

    @Override
    public void addProduct(String productId, int quantity) throws ECommerceException {
        this.basket = this.shopClient.addProductToBasket(basket, productId, quantity);
        this.refresh();
    }

    @Override
    public List<CartItem> getItems() {
        return this.items;
    }

    @Override
    public void removeProduct(String productId) throws ECommerceException {
        for ( ProductItem productItem : this.basket.getProduct_items() ) {
            if ( productItem.getProduct_id().equals(productId) ) {
                this.basket = this.shopClient.removeProductItemFromBasket(basket, productItem);
                this.refresh();
                break;
            }
        }
    }

    @Override
    public int count() {
        int count = 0;
        for ( CartItem item : this.items ) {
            count += item.getQuantity();
        }
        return count;
    }

    @Override
    public void clear() throws ECommerceException {
        // TODO: IMPLEMENT
    }

    @Override
    synchronized public void refresh() throws ECommerceException {
        this.items.clear();
        if ( basket.getProduct_items() != null ) {
            for (ProductItem productItem : basket.getProduct_items()) {
                ProductDetailResult result = this.detailService.getDetail(productItem.getProduct_id());
                Product product = result.getProductDetail();
                CartItem item = new GenericCartItem(product, (int) productItem.getQuantity(), new DemandwarePrice(productItem.getPrice(), product.getPrice().getCurrency()));
                this.items.add(item);
            }
        }
        // TODO: How to handle currency here in the best way?

        String currency = null;
        if ( this.items.size() > 0 ) {
            currency = this.items.get(0).getPrice().getCurrency();
        }
        this.totalPrice = new DemandwarePrice(basket.getProduct_total(), currency);
        this.count = this.items.size(); // TODO: FIX THIS
        // this.tax = basket.getTax_total() != null ? basket.getTax_total() : 0f;
    }

    @Override
    public ProductPrice getTotalPrice() throws ECommerceException {
        return this.totalPrice;
    }

    @Override
    public Map<URI, Object> getDataToExposeToClaimStore() throws ECommerceException {
        Map<URI,Object> claims = new HashMap<>();
        claims.put(Cart.CART_ITEMS_URI, this.count());
        claims.put(Cart.CART_TOTAL_PRICE_URI, this.getTotalPrice().getPrice());
        return claims;
    }

    @Override
    public String toString() {
        return "DemandwareCart {" +
                "id=" + this.getId() +
                ", items=" + count +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
