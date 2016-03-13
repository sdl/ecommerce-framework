package com.sdl.ecommerce.dxa.adf;

import com.sdl.ecommerce.api.ECommerceException;
import com.sdl.ecommerce.api.model.Cart;
import com.tridion.ambientdata.AmbientDataException;
import com.tridion.ambientdata.claimstore.ClaimStore;
import com.tridion.ambientdata.processing.AbstractClaimProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

/**
 * Cart Claim Processor
 * This ADF claim processor is designed to work in the same process space as the DXA web application.
 * If using SDL Web 8 it is not possible to use this within the micro service layer.
 *
 * @author nic
 */
public class CartClaimProcessor extends AbstractClaimProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(CartClaimProcessor.class);

    static final URI SESSION_ATTRIBUTES_URI = URI.create("taf:session:attributes");

    public CartClaimProcessor() {}

    @Override
    public void onRequestStart(ClaimStore claimStore) throws AmbientDataException {

        Cart cart = (Cart) claimStore.get(Cart.CART_URI);

        if ( cart == null ) {
            Map<String,Object> sessionAttributes = (Map<String,Object>) claimStore.get(SESSION_ATTRIBUTES_URI);
            cart = (Cart) sessionAttributes.get(Cart.CART_URI.toString());
            if ( cart != null ) {
                claimStore.put(Cart.CART_URI, cart);
            }
        }

        if ( cart != null ) {
            try {
                // TODO: Have some kind of cart.isChanged function here or E-TAG like identifier
                //
                Map<URI, Object> cartClaimValues = cart.getDataToExposeToClaimStore();
                for (URI uri : cartClaimValues.keySet()) {
                    claimStore.put(uri, cartClaimValues.get(uri));
                }
            } catch (ECommerceException e) {
                LOG.error("Could not get data from the E-Commerce Shopping Cart.", e);
            }
        }

    }

}
