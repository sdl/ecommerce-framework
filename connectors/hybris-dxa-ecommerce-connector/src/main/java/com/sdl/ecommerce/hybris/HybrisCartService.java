package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.hybris.api.HybrisClientManager;
import com.sdl.ecommerce.hybris.model.HybrisCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Hybris Cart Factory
 *
 * @author nic
 */
@Component
public class HybrisCartService implements CartService {

    @Autowired
    private HybrisClientManager hybrisClientManager;

    @Autowired
    private ProductDetailService productDetailService;

    @Override
    public Cart createCart() throws ECommerceException {
        String cartId = hybrisClientManager.getInstance().createCart();
        com.sdl.ecommerce.hybris.api.model.Cart cart = hybrisClientManager.getInstance().getCart(cartId);
        return new HybrisCart(cartId, cart, this.productDetailService);
    }

    @Override
    public Cart addProductToCart(String cartId, String sessionId, String productId, int quantity) throws ECommerceException {
        try {
            com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClientManager.getInstance().addItemToCart(cartId, productId, quantity);
            return new HybrisCart(cartId, cart, this.productDetailService);
        }
        catch ( Exception e ) {
            throw new ECommerceException("Could not add product to cart.", e);
        }
    }

    @Override
    public Cart removeProductFromCart(String cartId, String sessionId, String productId) throws ECommerceException {
        try {
            com.sdl.ecommerce.hybris.api.model.Cart cart = this.hybrisClientManager.getInstance().removeItemFromCart(cartId, productId);
            return new HybrisCart(cartId, cart, this.productDetailService);
        }
        catch ( Exception e ) {
            throw new ECommerceException("Could not remove product to cart.", e);
        }
    }

}
