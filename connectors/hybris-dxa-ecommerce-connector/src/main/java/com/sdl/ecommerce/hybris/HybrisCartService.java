package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.hybris.api.HybrisClientManager;
import com.sdl.ecommerce.hybris.model.HybrisCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return new HybrisCart(hybrisClientManager.getInstance(), productDetailService);
    }

    // TODO: Refactor to the new cart design here!!!

    @Override
    public Cart addProductToCart(String cartId, String productId, int quantity) throws ECommerceException {
        return null;
    }

    @Override
    public Cart removeProductFromCart(String cartId, String productId) throws ECommerceException {
        return null;
    }

    @Override
    public Cart clearCart(String cartId) throws ECommerceException {
        return null;
    }
}
