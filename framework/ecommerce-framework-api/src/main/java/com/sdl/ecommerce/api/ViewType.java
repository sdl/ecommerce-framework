package com.sdl.ecommerce.api;

/**
 * ViewType
 *
 * Enumeration of possible E-Commerce view types (aka page types)
 * Can be used to optimize the queries, such as trigger specific promotions, other ranking of listers etc
 *
 * @author nic
 */
public enum ViewType {

    HOME,
    SUMMARY,
    LISTER,
    SEARCH,
    DETAIL,
    COMPARE,
    FLYOUT
}
