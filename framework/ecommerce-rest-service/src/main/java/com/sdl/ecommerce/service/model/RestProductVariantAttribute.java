package com.sdl.ecommerce.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sdl.ecommerce.api.model.ProductVariantAttribute;
import graphql.annotations.GraphQLDescription;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;

@GraphQLDescription("E-Commerce Product Variant Attribute")
@GraphQLName("ProductVariantAttribute")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestProductVariantAttribute implements ProductVariantAttribute {

    private ProductVariantAttribute variantAttribute;

    public RestProductVariantAttribute(ProductVariantAttribute variantAttribute) {
        this.variantAttribute = variantAttribute;
    }

    @GraphQLField
    @Override
    public String getId() {
        return this.variantAttribute.getId();
    }

    @GraphQLField
    @Override
    public String getName() {
        return this.variantAttribute.getName();
    }

    @GraphQLField
    @Override
    public String getValueId() {
        return this.variantAttribute.getValueId();
    }

    @GraphQLField
    @Override
    public String getValue() {
        return this.variantAttribute.getValue();
    }
}
