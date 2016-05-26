package com.sdl.ecommerce.fredhopper;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * FredhopperEclItem
 *
 * @author nic
 */
@SemanticEntity(entityName = "ExternalContentLibraryStubSchemafredhopper", vocabulary = SDL_CORE, prefix = "s")
public class FredhopperEclItem extends ECommerceEclItem {
}
