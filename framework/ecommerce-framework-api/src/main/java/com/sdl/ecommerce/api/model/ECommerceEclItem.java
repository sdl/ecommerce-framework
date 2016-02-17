package com.sdl.ecommerce.api.model;

import com.sdl.webapp.common.api.model.entity.EclItem;

/**
 * ECommerce ECL Item
 *
 * @author nic
 */
public class ECommerceEclItem extends EclItem {

    /**
     * @return get external ID of the ECL item (category or product ID)
     */
    public String getExternalId() {
        return (String) this.getExternalMetadata().get("Id");
    }

    /**
     * @return get name used by the external system, such as category title, product name etc
     */
    public String getExternalName() {
        return (String) this.getExternalMetadata().get("Name");
    }
}
