package com.sdl.ecommerce.api.model;

/**
 * Product Variant Attribute
 *
 * @author nic
 */
public interface ProductVariantAttribute {

    /**
     * @return id of the variant attribute
     */
    String getId();

    /**
     * @return name of the variant attribute (can be localized)
     */
    String getName();

    /**
     * @return value identity
     */
    String getValueId();

    /**
     * @return presentable value (can be localized)
     */
    String getValue();
}
