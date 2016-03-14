package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.CartFactory;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientImpl;
import com.sdl.ecommerce.demandware.api.DemandwareShopClientManager;
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
public class DemandwareCartFactory implements CartFactory {

    @Autowired
    private ProductDetailService detailService;

    @Autowired
    private DemandwareShopClientManager shopClientManager;

    @Override
    public Cart createCart() throws ECommerceException {
        return new DemandwareCart(shopClientManager.getInstance(), detailService);
    }
}
