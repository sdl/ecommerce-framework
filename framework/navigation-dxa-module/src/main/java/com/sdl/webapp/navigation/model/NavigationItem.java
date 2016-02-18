package com.sdl.webapp.navigation.model;

import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticEntity;
import com.sdl.webapp.common.api.mapping.semantic.annotations.SemanticProperty;
import com.sdl.webapp.common.api.model.RichText;
import com.sdl.webapp.common.api.model.entity.AbstractEntityModel;
import com.sdl.webapp.common.api.model.entity.Image;
import com.sdl.webapp.common.api.model.entity.Link;

import static com.sdl.webapp.common.api.mapping.semantic.config.SemanticVocabulary.SDL_CORE;

/**
 * Navigation Item
 *
 * @author nic
 */
@SemanticEntity(entityName = "NavigationItem", vocabulary = SDL_CORE, prefix = "e", public_ = false)
public class NavigationItem extends AbstractEntityModel {

    @SemanticProperty("e:link")
    private Link link;

    @SemanticProperty("e:text")
    private RichText text;

    @SemanticProperty("e:image")
    private Image image;

    public Link getLink() {
        return link;
    }

    public RichText getText() {
        return text;
    }

    public Image getImage() {
        return image;
    }
}
