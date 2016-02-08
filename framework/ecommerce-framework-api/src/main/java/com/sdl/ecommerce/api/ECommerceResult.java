package com.sdl.ecommerce.api;

import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Promotion;

import java.util.List;

/**
 * ECommerceResult
 *
 * @author nic
 */
public interface ECommerceResult {

    List<Breadcrumb> getBreadcrumbs(String urlPrefix, String rootTitle);

    List<Promotion> getPromotions();
}
