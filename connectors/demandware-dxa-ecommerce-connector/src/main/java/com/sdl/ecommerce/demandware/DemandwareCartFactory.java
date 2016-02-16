package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.CartFactory;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.demandware.api.DemandwareShopClient;
import com.sdl.ecommerce.demandware.model.DemandwareCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DemandwareCartFactory
 *
 * @author nic
 */
@Component
public class DemandwareCartFactory implements CartFactory {

    @Autowired
    private ProductDetailService detailService;

    @Autowired
    private DemandwareShopClient shopClient;

    @Override
    public Cart createCart() throws ECommerceException {
        return new DemandwareCart(shopClient, detailService);
    }
}
