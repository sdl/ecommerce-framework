package com.sdl.ecommerce.api.model;

import java.util.List;
import java.util.Map;

/**
 * E-Commerce Product
 *
 * @author nic
 */
public interface Product {

    /**
     * Get product ID/SKU.
     * @return id
     */
    String getId();

    /**
     * Get name of the product. This can be a localized name based on current language.
     * @return name
     */
    String getName();

    /**
     * Get product price.
     * @return price
     */
    ProductPrice getPrice();

    /**
     * Get product description. This can be localized based on the current language.
     * @return description
     */
    String getDescription();

    // TODO: Have images based on type, primary, thumbnail, zoom etc

    /**
     * Get URL to thumbnail image.
     * @return url
     */
    String getThumbnailUrl();

    /**
     * Get URL to primary image (used on detail pages etc).
     * @return url
     */
    // TODO: How to handle image links? It should be possible to configure if the image should be proxied by the connector (if the E-Com instance is not available externally)
    String getPrimaryImageUrl();

    /**
     * Get categories the product belong to.
     * @return list of categories
     */
    List<Category> getCategories();

    /**
     * Get product facets.
     * @return list of product facets
     */
    // TODO: Convert this to an interface instead???
    List<FacetParameter> getFacets();

    /**
     * Get all additional attributes of the product. Can for example be used in compare views etc.
     * @return list of attributes
     */

    // TODO: Use Map<String,List<String>> here instead?? To make it easier to map over OData???
    // TODO: Have an AttributeValue representation here that indicate formatted value, raw value, type, single/multi value etc?

    Map<String,Object> getAttributes();
}
