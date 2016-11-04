package com.sdl.ecommerce.hybris;

import com.sdl.ecommerce.api.model.ECommerceEclItem;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Hybris ECL Item
 *
 * @author nic
 */


@SemanticEntity(entityName = "ExternalContentLibraryStubSchemahybris", vocabulary = SDL_CORE, prefix = "s")
public class HybrisEclItem extends ECommerceEclItem {
}
