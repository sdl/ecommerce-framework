package com.sdl.ecommerce.api;

import java.util.HashMap;
import java.util.Map;

/**
 * QueryF ilter
 *
 * @author nic
 */
public class QueryFilter {

    private Map<String,String> attributes = new HashMap<>();

    public QueryFilter() {}

    public QueryFilter attribute(String name, String value) {
        this.attributes.put(name, value);
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getAttribute(String name) {
        return this.attributes.get(name);
    }
}
