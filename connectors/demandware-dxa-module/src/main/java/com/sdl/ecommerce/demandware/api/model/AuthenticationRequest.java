package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * AuthenticationType
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    public static String GUEST = "guest";
    public static String CREDENTIALS = "credentials";
    public static String REFRESH = "refresh";
    public static String SESSION = "session";

    private String type;

    public AuthenticationRequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
