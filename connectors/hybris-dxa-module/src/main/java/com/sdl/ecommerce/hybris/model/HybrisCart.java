package com.sdl.ecommerce.hybris.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.ProductPrice;
import com.sdl.ecommerce.api.model.impl.GenericCartItem;
import com.sdl.ecommerce.hybris.api.HybrisClient;
import com.sdl.ecommerce.hybris.api.model.Entry;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hybris Cart
 *
 * @author nic
 */
public class HybrisCart implements Cart {

    private ProductDetailService detailService;
    private HybrisClient hybrisClient;
    private List<CartItem> items = new ArrayList<>();
    private String id;
    private int count = 0;
    private ProductPrice totalPrice = null;
    private float shipping = 0f;
    private float tax = 0f;
    private String sessionId = null;
    private com.sdl.ecommerce.hybris.api.model.Cart cart = null;


    public HybrisCart(HybrisClient hybrisClient, ProductDetailService detailService) {
        this.hybrisClient = hybrisClient;
        this.detailService = detailService;
        this.sessionId = this.hybrisClient.createCart();
        this.cart = this.hybrisClient.getCart(this.sessionId);
        this.id = this.cart.getCode();
    }

    @Override
    public String getId() throws ECommerceException {
        return id;
    }


    @Override
    public void addProduct(String productId) throws ECommerceException {
        this.addProduct(productId, 1);
    }


    @Override
    public void addProduct(String productId, int quantity) throws ECommerceException {
        try {
            if ( this.sessionId == null ) {
                this.sessionId = this.hybrisClient.createCart();
            }
            com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClient.addItemToCart(this.sessionId, productId, quantity);
            this.refresh(cart);
        }
        catch ( Exception e ) {
            throw new ECommerceException("Could not add product to cart.", e);
        }
    }

    @Override
    public void removeProduct(String productId) throws ECommerceException {
        try {
            if ( this.sessionId == null ) {
                this.sessionId = this.hybrisClient.createCart();
            }
            com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClient.removeItemFromCart(this.sessionId, productId);
            this.refresh(cart);
        }
        catch ( Exception e ) {
            throw new ECommerceException("Could not add product to cart.", e);
        }
    }

    synchronized private void refresh(com.sdl.ecommerce.hybris.api.model.Cart cart) throws ECommerceException {
        this.items.clear();
        if ( cart.getEntries() != null ) {
            for ( Entry entry : cart.getEntries() ) {
                ProductDetailResult productDetailResult = this.detailService.getDetail(entry.getProduct().getCode());
                CartItem item = new GenericCartItem(productDetailResult.getProductDetail(), entry.getQuantity(), new HybrisPrice(entry.getBasePrice()));
                this.items.add(item);
            }
        }

        this.id = cart.getCode();
        this.totalPrice = new HybrisPrice(cart.getTotalPrice());
        this.shipping = 0f; // TODO: How to calculate shipping costs
        if ( cart.getTotalTax() != null ) {
            this.tax = Float.parseFloat(cart.getTotalTax().getValue());
        }
        else {
            this.tax = 0f;
        }
        this.count = cart.getTotalUnitCount();
        // TODO: Add discounts as well!!!!!!
    }

    @Override
    public List<CartItem> getItems() throws ECommerceException {
        return this.items;
    }

    @Override
    public int count() throws ECommerceException {
        return this.count;
    }

    @Override
    public ProductPrice getTotalPrice() throws ECommerceException {
        return this.totalPrice;
    }

    // TODO: SHOULD WE EXPOSE THESE KIND OF VALUES AS WELL????
    public float getShipping() {
        return shipping;
    }

    public float getTax() {
        return tax;
    }

    @Override
    public void clear() throws ECommerceException {

        // TODO: Implement a proper clear here...
        this.sessionId = this.hybrisClient.createCart();
        com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClient.getCart(this.sessionId);
        this.refresh(cart);
    }

    @Override
    public void refresh() throws ECommerceException {
        if ( this.sessionId != null ) {
            com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClient.getCart(this.sessionId);
            this.refresh(cart);
        }
    }

    @Override
    public Map<URI,Object> getDataToExposeToClaimStore() throws ECommerceException {

        Map<URI,Object> claims = new HashMap<>();
        claims.put(Cart.CART_ITEMS_URI, this.count());
        claims.put(Cart.CART_TOTAL_PRICE_URI, this.getTotalPrice().getPrice());
        return claims;
    }

    @Override
    public String toString() {
        return "HybrisCart {" +
                "id=" + id +
                ", items=" + count +
                ", totalPrice=" + totalPrice +
                ", shipping=" + shipping +
                ", tax=" + tax +
                '}';
    }
}
