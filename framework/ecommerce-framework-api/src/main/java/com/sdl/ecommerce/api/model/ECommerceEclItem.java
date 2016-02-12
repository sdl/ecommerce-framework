package com.sdl.ecommerce.api.model;

import com.sdl.webapp.common.api.model.entity.EclItem;

/**
 * ECommerceEclItem
 *
 * @author nic
 */
public class ECommerceEclItem extends EclItem {

    public String getExternalId() {
        return (String) this.getExternalMetadata().get("Id");
    }

    public String getExternalName() {
        return (String) this.getExternalMetadata().get("Name");
    }
}
