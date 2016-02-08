package com.sdl.ecommerce.api.model;

/**
 * Editable
 *
 * @author nic
 */
public interface Editable {

    String getEditUrl();

    // This interface is used by components that are editable
    // So then this can selected on the implementation as such (e.g.Fredhopper) if the item is editable
    //
}
