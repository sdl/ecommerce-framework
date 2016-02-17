package com.sdl.ecommerce.dxa.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Search Box
 *
 * @author nic
 */
@SemanticEntity(entityName = "SearchBox", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class SearchBox extends AbstractEntityModel {

}
