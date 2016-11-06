package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Fault
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fault {

    private String type;

    private String message;

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
