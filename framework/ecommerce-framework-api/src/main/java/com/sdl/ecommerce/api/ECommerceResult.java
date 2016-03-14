package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.List;

/**
 * ECommerce Result
 * Generic base interface for all types of results (details, queries etc).
 *
 * @author nic
 */
public interface ECommerceResult {

    /**
     * Get current breadcrumbs
     * @param urlPrefix
     * @param rootTitle
     * @return breadcrumbs
     */
    List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle);

    /**
     * Get promotions active for current result.
     * @return promotions
     */
    List<Promotion> getPromotions();
}
