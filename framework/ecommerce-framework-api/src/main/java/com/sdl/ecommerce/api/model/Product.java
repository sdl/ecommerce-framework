package com.sdl.ecommerce.api.model;

import java.util.List;
import java.util.Map;

/**
 * Product
 *
 * @author nic
 */
public interface Product {

    // TODO: Have a list of categories it belongs to

    String getId();

    String getName();

    ProductPrice getPrice();

    String getDescription();

    // TODO: Have images based on type, primary, thumbnail, zoom etc

    String getThumbnailUrl();

    String getPrimaryImageUrl();

    String getDetailPageUrl();

    List<Category> getCategories();

    List<FacetParameter> getFacets();

    Map<String,Object> getAttributes();
}
