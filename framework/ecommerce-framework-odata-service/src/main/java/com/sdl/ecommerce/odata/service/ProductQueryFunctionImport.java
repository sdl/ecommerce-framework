package com.sdl.ecommerce.odata.service;

import com.sdl.odata.api.edm.annotations.EdmFunctionImport;

/**
 * ProductQueryFunctionImport
 *
 * @author nic
 */
@EdmFunctionImport(
        name = "ProductQuery",
        function = "ProductQueryFunction",
        namespace = "SDL.ECommerce"
)
public class ProductQueryFunctionImport {
}
