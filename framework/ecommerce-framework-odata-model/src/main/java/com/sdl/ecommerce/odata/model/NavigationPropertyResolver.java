package com.sdl.ecommerce.odata.model;

/**
 * NavigationPropertyResolver
 *
 * @author nic
 */
public interface NavigationPropertyResolver {

    Object resolve(String key, String propertyName);
}
