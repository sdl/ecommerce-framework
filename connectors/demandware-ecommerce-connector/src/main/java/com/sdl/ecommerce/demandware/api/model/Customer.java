package com.sdl.ecommerce.demandware.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Customer
 *
 * @author nic
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private String customer_id;

}
