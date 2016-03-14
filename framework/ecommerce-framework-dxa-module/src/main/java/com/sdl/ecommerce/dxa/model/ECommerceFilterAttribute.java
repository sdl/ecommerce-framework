package com.sdl.ecommerce.dxa.model;

import com.sdl.ecommerce.api.QueryFilterAttribute;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * ECommerce Filter Attribute
 *
 * @author nic
 */
@SemanticEntity(entityName = "ECommerceFilterAttribute", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class ECommerceFilterAttribute extends AbstractEntityModel {

    @SemanticProperty("e:name")
    private String name;

    @SemanticProperty("e:value")
    private String value;

    @SemanticProperty("e:mode")
    private String mode;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getMode() {
        return mode;
    }

    /**
     * Convert to filter attribute to be used in E-Commerce queries.
     * @return filter attribute
     */
    public QueryFilterAttribute toQueryFilterAttribute() {
        QueryFilterAttribute.FilterMode filterMode;
        if ( mode != null && mode.equals("exclude") ) {
            filterMode = QueryFilterAttribute.FilterMode.EXCLUDE;
        }
        else {
            filterMode = QueryFilterAttribute.FilterMode.INCLUDE;
        }
        return new QueryFilterAttribute(name, value, filterMode);
    }
}
