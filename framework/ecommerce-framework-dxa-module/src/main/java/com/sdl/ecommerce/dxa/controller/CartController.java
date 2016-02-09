package com.sdl.ecommerce.dxa.controller;

import com.sdl.ecommerce.api.CartFactory;
import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.dxa.model.CartWidget;
import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.api.model.MvcData;
import com.sdl.webapp.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Cart Controller
 *
 * @author nic
 */
@Controller
@RequestMapping("/")
public class CartController extends BaseController {

    @Autowired(required = false)
    private CartFactory cartFactory;

    @RequestMapping(method = RequestMethod.GET, value = "/ajax/cart/addProduct/{productId}")
    public @ResponseBody
    String addProductToCart(HttpServletRequest request, @PathVariable String productId) throws ECommerceException {
        if ( this.cartFactory == null ) {
            return "0";
        }
        Cart cart = this.getCart(request);
        if ( cart == null ) {
            cart = this.cartFactory.createCart();
            this.storeCart(request, cart);
        }
        cart.addProduct(productId);
        return Integer.toString(cart.count());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ajax/cart/removeProduct/{productId}")
    public @ResponseBody
    String removeProductToCart(HttpServletRequest request, @PathVariable String productId) throws ECommerceException {
        if ( this.cartFactory == null ) {
            return "0";
        }
        Cart cart = this.getCart(request);
        if ( cart == null ) {
            cart = this.cartFactory.createCart();
            this.storeCart(request, cart);
        }
        cart.removeProduct(productId);
        return Integer.toString(cart.count());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/system/mvc/ECommerce/CartController/Entity/{entityId}")
    public String handleCart(HttpServletRequest request, @PathVariable String entityId) throws ContentProviderException {

        CartWidget entity = (CartWidget) this.getEntityFromRequest(request, entityId);
        entity.setCart(this.getCart(request));
        request.setAttribute("entity", entity);

        final MvcData mvcData = entity.getMvcData();
        return resolveView(mvcData, "Entity", request);
    }


    private Cart getCart(HttpServletRequest request) {
        return (Cart) request.getSession().getAttribute(Cart.CART_URI.toString());
    }

    private void storeCart(HttpServletRequest request, Cart cart) {
        request.getSession().setAttribute(Cart.CART_URI.toString(), cart);
    }
}
