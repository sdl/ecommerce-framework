package com.sdl.ecommerce.odata.service;

import com.sdl.odata.api.service.ODataRequestContext;

/**
 * ODataRequestContextHolder
 *
 * @author nic
 */
public class ODataRequestContextHolder {

    private static ThreadLocal<ODataRequestContext> requestContext = new ThreadLocal<>();

    public static ODataRequestContext get() {
        return requestContext.get();
    }

    public static void set(ODataRequestContext context) {
        requestContext.set(context);
    }

    public static void clear() {
        requestContext.remove();
    }
}
