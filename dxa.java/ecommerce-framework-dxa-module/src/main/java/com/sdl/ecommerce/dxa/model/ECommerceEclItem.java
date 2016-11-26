package com.sdl.ecommerce.dxa.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntities;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.model.entity.EclItem;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * ECommerce ECL Item
 *
 * @author nic
 */
@SemanticEntities({

        // All available ECL E-Commerce types
        //
        // Due to a limitation in DXA.Java all various ECL items needs to be registered here. In DXA.NET this does work better.
        //
        @SemanticEntity(entityName = "ExternalContentLibraryStubSchemafredhopper", vocabulary = SDL_CORE, prefix = "f"),
        @SemanticEntity(entityName = "ExternalContentLibraryStubSchemademandware", vocabulary = SDL_CORE, prefix = "d"),
        @SemanticEntity(entityName = "ExternalContentLibraryStubSchemahybris", vocabulary = SDL_CORE, prefix = "h")
})
public class ECommerceEclItem extends EclItem {

    // TODO: Should this be part of the DXA module instead???

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
