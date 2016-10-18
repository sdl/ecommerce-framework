package com.sdl.ecommerce.odata.function;

import com.sdl.ecommerce.api.CartService;
import com.sdl.ecommerce.api.model.Cart;
import com.sdl.ecommerce.odata.datasource.ProductDataSource;
import com.sdl.ecommerce.odata.model.ODataCart;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.ODataNotImplementedException;
import com.sdl.odata.api.edm.annotations.EdmFunction;
import com.sdl.odata.api.edm.annotations.EdmReturnType;
import com.sdl.odata.api.edm.model.Operation;
import com.sdl.odata.api.processor.datasource.factory.DataSourceFactory;
import com.sdl.odata.api.service.ODataRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        type = "Carts"
)
public class CreateCartFunction implements Operation<ODataCart> {

    private static final Logger LOG = LoggerFactory.getLogger(CreateCartFunction.class);

    @Override
    public ODataCart doOperation(ODataRequestContext oDataRequestContext, DataSourceFactory dataSourceFactory) throws ODataException {

        ProductDataSource productDataSource = (ProductDataSource) dataSourceFactory.getDataSource(oDataRequestContext, "SDL.ECommerce.Product");
        CartService cartService = productDataSource.getCartService();
        if ( cartService != null ) {
            Cart cart = cartService.createCart();
            return new ODataCart(cart);
        }
        throw new ODataNotImplementedException("No cart service found!");
    }
}
