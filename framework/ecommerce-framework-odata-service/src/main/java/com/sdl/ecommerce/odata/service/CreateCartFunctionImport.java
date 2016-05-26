package com.sdl.ecommerce.odata.service;

import com.sdl.odata.api.edm.annotations.EdmFunctionImport;

/**
 * CreateCartFunctionImport
 *
 * @author nic
 */
@EdmFunctionImport(
        name = "CreateCart",
        function = "CreateCartFunction",
        namespace = "SDL.ECommerce"
        )
public class CreateCartFunctionImport {
}
