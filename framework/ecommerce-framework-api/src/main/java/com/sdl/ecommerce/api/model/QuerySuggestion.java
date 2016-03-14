package com.sdl.ecommerce.api.model;

/**
 * Query Suggestion
 * Gives a suggestion on mistyped search phrase.
 *
 * @author nic
 */
public interface QuerySuggestion {

    /**
     * @return original search phrase
     */
    String getOriginal();

    /**
     * @return suggestion on search phrase
     */
    String getSuggestion();

}
