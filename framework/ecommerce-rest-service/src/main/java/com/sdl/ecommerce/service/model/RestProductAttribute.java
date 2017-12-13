package com.sdl.ecommerce.service.model;

import graphql.annotations.GraphQLDescription;
import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;

import java.util.Collections;
import java.util.List;

/**
 * RestProductAttribute
 *
 * @author nic
 */
@GraphQLName("ProductAttribute")
@GraphQLDescription("Product Attribute")
public class RestProductAttribute {

    @GraphQLField
    @GraphQLDescription("Name")
    private String name;

    @GraphQLField
    @GraphQLDescription("Indicates if the attribute is a single value or not")
    private boolean singleValue;

    @GraphQLField
    @GraphQLDescription("Product attribute value(s)")
    private List<String> values;

    // TODO: Can we include the attributes in the type specification as well?

    public RestProductAttribute(String name, String value) {
        this.name = name;
        this.values = Collections.singletonList(value);
        this.singleValue = true;
    }

    public RestProductAttribute(String name, List<String> values) {
        this.name = name;
        this.values = values;
        this.singleValue = false;
    }

    public String getName() {
        return name;
    }

    public boolean isSingleValue() {
        return singleValue;
    }

    public List<String> getValues() {
        return values;
    }
}
