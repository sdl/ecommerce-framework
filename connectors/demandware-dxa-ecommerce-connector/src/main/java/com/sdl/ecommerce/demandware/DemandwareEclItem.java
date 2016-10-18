package com.sdl.ecommerce.demandware;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Demandware ECL Item
 *
 * @author nic
 */
@SemanticEntity(entityName = "ExternalContentLibraryStubSchemademandware", vocabulary = SDL_CORE, prefix = "s")
public class DemandwareEclItem extends ECommerceEclItem {
}
