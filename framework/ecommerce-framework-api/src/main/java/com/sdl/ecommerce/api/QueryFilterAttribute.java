package com.sdl.ecommerce.api;

/**
 * Query Filter Attribute.
 * Used for include/exclude information in E-Commerce queries.
 *
 * @author nic
 */
public class QueryFilterAttribute {

    public enum FilterMode {
        INCLUDE,
        EXCLUDE
    }

    private String name;
    private String value;
    private FilterMode mode;

    public QueryFilterAttribute(String name, String value) {
        this(name, value, FilterMode.INCLUDE);
    }

    public QueryFilterAttribute(String name, String value, FilterMode mode) {
        this.name = name;
        this.value = value;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public FilterMode getMode() {
        return mode;
    }

}
