package com.sdl.ecommerce.odata.service;

import com.sdl.ecommerce.odata.model.Cart;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.edm.annotations.EdmFunction;
import com.sdl.odata.api.edm.annotations.EdmParameter;
import com.sdl.odata.api.edm.annotations.EdmReturnType;
import com.sdl.odata.api.edm.model.Operation;
import com.sdl.odata.api.processor.datasource.factory.DataSourceFactory;
import com.sdl.odata.api.service.ODataRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Create Cart Function
 *
 * @author nic
 */
@EdmFunction(
        name = "CreateCartFunction",
        namespace = "SDL.ECommerce",
        isBound = false
)
@EdmReturnType(
        type = "SDL.ECommerce.Cart"
)
public class CreateCartFunction implements Operation<Cart> {

    private static final Logger LOG = LoggerFactory.getLogger(CreateCartFunction.class);

    @EdmParameter
    private int cartId;

    @EdmParameter
    private String facet;

    @Override
    public Cart doOperation(ODataRequestContext oDataRequestContext, DataSourceFactory dataSourceFactory) throws ODataException {
        LOG.debug("CART ID: " + cartId);
        LOG.debug("TEST FACET: " + facet);
        return new Cart();
    }
}
