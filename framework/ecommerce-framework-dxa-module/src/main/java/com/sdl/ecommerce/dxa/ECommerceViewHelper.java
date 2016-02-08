package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.model.Editable;
import org.springframework.stereotype.Component;

/**
 * ECommerceViewHelper
 *
 * @author nic
 */
@Component
public class ECommerceViewHelper {

    public boolean showEditControls(Object ecommerceItem) {
        return (ecommerceItem instanceof Editable);
    }
}
