package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.model.Editable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ECommerceViewHelper
 *
 * @author nic
 */
@Component
public class ECommerceViewHelper {

    @Autowired
    HttpServletRequest request;

    public boolean showEditControls(Object ecommerceItem) {
        Boolean hasBeenInvokedViaXpm = (Boolean) request.getSession().getAttribute(ECommerceSessionAttributes.IN_XPM_SESSION);
        return Boolean.TRUE.equals(hasBeenInvokedViaXpm) &&
               ecommerceItem instanceof Editable;
    }
}
