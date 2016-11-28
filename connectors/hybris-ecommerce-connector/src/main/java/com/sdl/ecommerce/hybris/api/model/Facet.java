package com.sdl.ecommerce.hybris.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Facet
 *
 * @author nic
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class Facet {

    @JsonProperty("name")
    private String name;

    @JsonProperty("multiSelect")
    private boolean multiSelect;

    @JsonProperty("category")
    private boolean category;

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("values")
    private List<FacetValue> values;

    @JsonProperty("topValues")
    private List<FacetValue> topValues;

    public String getId() {

        // TODO: This is not bullet proof approach -> rethink...
        String id = null;
        if ( values != null && values.size() > 0 ) {

            // Extract the ID from the query parameter of the facet values
            //
            String query = values.get(0).getQuery();
            StringTokenizer tokenizer = new StringTokenizer(query, ":");
            int tokens = tokenizer.countTokens();
            for ( int i=0; i < tokens-2; i++) {
                tokenizer.nextToken();
            }
            id = tokenizer.nextToken();

        }
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCategory() {
        return category;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public int getPriority() {
        return priority;
    }

    public List<FacetValue> getValues() {
        return values;
    }

    public List<FacetValue> getTopValues() {
        return topValues;
    }
}
