package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
import com.sdl.ecommerce.demandware.api.model.Basket;
import com.sdl.ecommerce.demandware.api.model.ProductItem;
import com.sdl.ecommerce.demandware.model.DemandwareCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Demandware Cart Factory
 * Creates a new cart on the session.
 *
 * @author nic
 */
@Component
public class DemandwareCartService implements CartService {

    @Autowired
    private ProductDetailService detailService;

    @Autowired
    private DemandwareShopClientManager shopClientManager;

    @Override
    public Cart createCart() throws ECommerceException {
        Basket basket = shopClientManager.getInstance().createBasket();
        return new DemandwareCart(basket, detailService);
    }

    @Override
    public Cart addProductToCart(String cartId, String sessionId, String productId, int quantity) throws ECommerceException {
        Basket basket = this.shopClientManager.getInstance().getBasket(cartId, sessionId);
        basket = this.shopClientManager.getInstance().addProductToBasket(basket, productId, quantity);
        return new DemandwareCart(basket, detailService);
    }

    @Override
    public Cart removeProductFromCart(String cartId, String sessionId, String productId) throws ECommerceException {
        Basket basket = this.shopClientManager.getInstance().getBasket(cartId, sessionId);
        for ( ProductItem productItem : basket.getProduct_items() ) {
            if ( productItem.getProduct_id().equals(productId) ) {
                basket = this.shopClientManager.getInstance().removeProductItemFromBasket(basket, productItem);
                break;
            }
        }
        return new DemandwareCart(basket, detailService);
    }

}
