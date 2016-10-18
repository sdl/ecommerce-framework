package com.sdl.ecommerce.odata.function;

import com.sdl.odata.api.edm.annotations.EdmFunctionImport;

/**
 * AddToCartFunctionImport
 *
 * @author nic
 */
@EdmFunctionImport(
        name = "AddProductToCart",
        function = "AddProductToCartFunction",
        namespace = "SDL.ECommerce"
)
public class AddProductToCartFunctionImport {
}
