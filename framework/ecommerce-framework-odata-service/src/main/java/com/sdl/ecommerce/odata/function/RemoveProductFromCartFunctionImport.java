package com.sdl.ecommerce.odata.function;

import com.sdl.odata.api.edm.annotations.EdmFunctionImport;

/**
 * RemoveFromCartFunctionImport
 *
 * @author nic
 */
@EdmFunctionImport(
        name = "RemoveProductFromCart",
        function = "RemoveProductFromCartFunction",
        namespace = "SDL.ECommerce"
)
public class RemoveProductFromCartFunctionImport {
}
