package com.sdl.ecommerce.api.model;

import java.util.List;

/**
 * Product Variant Attribute Type
 *
 * @author nic
 */
public interface ProductVariantAttributeType {

    /**
     * @return attribute id
     */
    String getId();

    /**
     * @return attribute name (can be localized)
     */
    String getName();

    /**
     * @return available attribute values for current product
     */
    List<ProductVariantAttributeValueType> getValues();
}
