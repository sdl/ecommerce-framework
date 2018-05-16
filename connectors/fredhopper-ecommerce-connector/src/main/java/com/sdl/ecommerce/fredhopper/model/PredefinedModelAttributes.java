package com.sdl.ecommerce.fredhopper.model;

import java.util.Arrays;

/**
 * Predefined Model Attributes
 */
public enum PredefinedModelAttributes {
    masterId,
    variantId,
    name,
    description,
    price,
    thumbnailUrl,
    primaryImageUrl;

    static boolean isDefined(String attributeValue) {
        return Arrays.stream(PredefinedModelAttributes.values())
                .anyMatch(value -> value.name().equals(attributeValue));
    }
}

