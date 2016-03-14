package com.sdl.ecommerce.dummy;

import com.sdl.ecommerce.api.CartFactory;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.ProductDetailService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.dummy.model.DummyCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dummy Cart Factory.
 * Creates a dummy cart which is handy in test & demo situations.
 *
 * @author nic
 */
@Component
public class DummyCartFactory implements CartFactory {

    @Autowired
    private ProductDetailService detailService;

    @Override
    public Cart createCart() throws ECommerceException {
        return new DummyCart(this.detailService);
    }
}
