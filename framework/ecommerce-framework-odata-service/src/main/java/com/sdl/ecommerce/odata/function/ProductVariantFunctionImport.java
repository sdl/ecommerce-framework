package com.sdl.ecommerce.odata.function;

import com.sdl.odata.api.edm.annotations.EdmFunctionImport;

/**
 * ProductVariantFunctionImport
 *
 * @author nic
 */
@EdmFunctionImport(
        name = "ProductVariant",
        function = "ProductVariantFunction",
        namespace = "SDL.ECommerce"
)
public class ProductVariantFunctionImport {
}
