package com.sdl.ecommerce.dummy.model;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.api.model.CartItem;
import com.sdl.ecommerce.api.model.ProductPrice;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dummy Cart
 *
 * @author nic
 */
public class DummyCart implements Cart {

    private List<CartItem> items = new ArrayList<>();
    private ProductDetailService detailService;

    public DummyCart(ProductDetailService detailService) {
        this.detailService = detailService;
    }

    @Override
    public String getId() {
        return Integer.toString(this.hashCode());
    }

    public void addProduct(String productId) throws ECommerceException {
        this.addProduct(productId, 1);
    }

    public void addProduct(String productId, int quantity) throws ECommerceException {

        boolean itemAlreadyInCart = false;
        for ( CartItem item : this.items ) {
            if ( item.getProduct() != null && item.getProduct().getId().equals(productId) ) {
                ((DummyCartItem) item).setQuantity(item.getQuantity()+quantity);
                itemAlreadyInCart = true;
                break;
            }
        }
        if ( !itemAlreadyInCart ) {
            ProductDetailResult result = this.detailService.getDetail(productId);
            this.items.add(new DummyCartItem(result.getProductDetail(), quantity));
        }

    }

    @Override
    public List<CartItem> getItems() {
        return this.items;
    }

    public void removeProduct(String productId) throws ECommerceException {
        for ( int i=0; i < this.items.size(); i++ ) {
            CartItem item = this.items.get(i);
            if ( item.getProduct() != null && item.getProduct().getId().equals(productId) ) {
                this.items.remove(item);
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

    public void clear() throws ECommerceException {
       this.items.clear();
    }

    public void refresh() throws ECommerceException {}

    @Override
    public ProductPrice getTotalPrice() throws ECommerceException {
        DummyPrice totalPrice = null;
        for ( CartItem item: this.items ) {
            if ( totalPrice == null ) {
                totalPrice = new DummyPrice(item.getPrice(), item.getQuantity());
            }
            else {
                totalPrice.add(item.getPrice().getPrice()*item.getQuantity());
            }
        }
        return totalPrice;
    }

    @Override
    public Map<URI, Object> getDataToExposeToClaimStore() throws ECommerceException {
        Map<URI,Object> claims = new HashMap<>();
        claims.put(Cart.CART_ITEMS_URI, this.count());
        ProductPrice totalPrice = this.getTotalPrice();
        if ( totalPrice != null ) {
            claims.put(Cart.CART_TOTAL_PRICE_URI, this.getTotalPrice().getPrice());
        }
        else {
            claims.put(Cart.CART_TOTAL_PRICE_URI, 0.0f);
        }
        return claims;
    }

}
