package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.CartFactory;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.hybris.api.HybrisClient;
import com.sdl.ecommerce.hybris.model.HybrisCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Hybris Cart Factory
 *
 * @author nic
 */
@Component
public class HybrisCartFactory implements CartFactory {

    @Autowired
    private HybrisClient hybrisClient;

    @Autowired
    private ProductDetailService productDetailService;

    @Override
    public Cart createCart() throws ECommerceException {
        return new HybrisCart(hybrisClient, productDetailService);
    }
}
