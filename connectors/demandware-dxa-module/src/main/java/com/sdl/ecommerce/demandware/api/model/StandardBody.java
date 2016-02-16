package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class StandardBody {

    @JsonProperty("_v")
    private String version;

    private Fault fault;

    public Fault getFault() {
        return fault;
    }

    public String getVersion() {
        return version;
    }
}
