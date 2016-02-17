package com.sdl.ecommerce.api;

/**
 * ECommerce Exception
 *
 * @author nic
 */
public class ECommerceException extends RuntimeException {

    public ECommerceException(String message) {
        super(message);
    }

    public ECommerceException(String message, Throwable exception) {
        super(message, exception);
    }

    public ECommerceException(Throwable exception) {
        super(exception);
    }
}
