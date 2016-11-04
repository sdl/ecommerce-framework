package com.sdl.ecommerce.api.model;

/**
 * Product Variant Attribute Value Type
 *
 * @author nic
 */
public interface ProductVariantAttributeValueType {

    /**
     * @return value id
     */
    String getId();

    /**
     * @return value (can be localized)
     */
    String getValue();

    /**
     * @return if selected
     */
    boolean isSelected();
}
