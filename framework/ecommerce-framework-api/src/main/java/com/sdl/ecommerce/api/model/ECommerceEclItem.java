package com.sdl.ecommerce.api.model;

import com.sdl.webapp.common.api.model.entity.EclItem;

/**
 * ECommerce ECL Item
 *
 * @author nic
 */
public class ECommerceEclItem extends EclItem {

    /**
     * @return external ID of the ECL item (category or product ID)
     */
    public String getExternalId() {
        if ( this.getExternalMetadata() != null ) {
            return (String) this.getExternalMetadata().get("Id");
        }
        return this.getFileName();
    }

    /**
     * @return name used by the external system, such as category title, product name etc
     */
    public String getExternalName() {
        if ( this.getExternalMetadata() != null ) {
            return (String) this.getExternalMetadata().get("Name");
        }
        return null;
    }
}
