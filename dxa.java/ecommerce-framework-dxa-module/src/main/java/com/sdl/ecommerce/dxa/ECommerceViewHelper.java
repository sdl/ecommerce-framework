package com.sdl.ecommerce.dxa;

import com.sdl.ecommerce.api.model.Editable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ECommerce View Helper
 * Contains various helper functions to be used in JSP views.
 *
 * @author nic
 */
@Component
public class ECommerceViewHelper {

    @Autowired
    HttpServletRequest request;

    /**
     * Determine if edit controls should be shown for current E-Commerce item (e.g. facet group, promotion etc)
     *
     * @param editableEcommerceItem
     * @return true if edit controls can be shown, otherwise false
     */
    public boolean showEditControls(Editable editableEcommerceItem) {
        return hasBeenInvokedViaXpm() &&
               editableEcommerceItem.getEditUrl() != null;
    }

    /**
     * Determine if an XPM session is active or not
     * @return true if XPM session is active, otherwise false
     */
    public boolean hasBeenInvokedViaXpm() {
        Boolean hasBeenInvokedViaXpm = (Boolean) request.getSession().getAttribute(ECommerceSessionAttributes.IN_XPM_SESSION);
        return Boolean.TRUE.equals(hasBeenInvokedViaXpm);
    }
}
