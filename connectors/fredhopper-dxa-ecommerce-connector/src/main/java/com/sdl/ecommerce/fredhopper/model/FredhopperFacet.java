package com.sdl.ecommerce.fredhopper.model;

import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.model.Breadcrumb;
import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.api.model.FacetParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fredhopper Facet
 *
 * @author nic
 */
public class FredhopperFacet implements Facet {

    // TODO: Use a generic Facet implementation here!!

    private String id;
    private String title;
    private int count;
    private boolean isSelected;
    private boolean isCategory;
    private List<String> values = new ArrayList<>();
    private FacetType type;

    static private Pattern FH_RANGE_PATTERN = Pattern.compile("([0-9]+[\\.0-9]*)<[A-za-z_0-9]+<([0-9]+[\\.0-9]*)");
    static private Pattern FH_LESS_THAN_PATTERN = Pattern.compile("[A-za-z_0-9]+<([0-9]+[\\.0-9]*)");
    static private Pattern FH_GREATER_THAN_PATTERN = Pattern.compile("[A-za-z_0-9]+>([0-9]+[\\.0-9]*)");
    static private Pattern FH_BAD_FLOAT_PATTERN = Pattern.compile("[0-9]+\\.[0-9][0-9][0-9]+");

    public FredhopperFacet(Filter fhFacetGroup, Filtersection fhFacet) {
        this.id =  fhFacetGroup.getOn();
        this.title = fhFacet.getLink().getName();
        this.isSelected = fhFacet.isSelected() != null && fhFacet.isSelected();
        if ( fhFacetGroup.getBasetype() != null ) {
            this.isCategory = fhFacetGroup.getBasetype().value().equals("cat");
        }
        this.count = fhFacet.getNr();
        this.getFacetValueAndType(fhFacet, fhFacetGroup.getBasetype().value());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    // ----
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public FacetType getType() {
        return this.type;
    }

    @Override
    public String getValue() {
        if ( !this.values.isEmpty() ) {
            return this.values.get(0);
        }
        return null;
    }

    @Override
    public List<String> getValues() {
        return this.values;
    }

    @Override
    public boolean isCategory() {
        return this.isCategory;
    }

    private void getFacetValueAndType(Filtersection fhFacet, String facetType) {
        String facetValue = fhFacet.getValue().getValue();
        Matcher rangeMatcher = FH_RANGE_PATTERN.matcher(facetValue);
        Matcher ltMatcher = FH_LESS_THAN_PATTERN.matcher(facetValue);
        Matcher gtMatcher = FH_GREATER_THAN_PATTERN.matcher(facetValue);

        if ( rangeMatcher.matches() ) {
            String min = formatRangeValue(rangeMatcher.group(1));
            String max = formatRangeValue(rangeMatcher.group(2));
            values.add(min);
            values.add(max);
            type = FacetType.RANGE;
        }
        else if ( ltMatcher.matches() ) {
           values.add(ltMatcher.group(1));
            type = FacetType.LESS_THAN;
        }
        else if ( gtMatcher.matches() ) {
            values.add(gtMatcher.group(1));
            type = FacetType.GREATER_THAN;
        }
        /*
        else if ( facetType.equals("float") || facetType.equals("int") || facetType.equals("text") ) {
            values.add(facetValue);
            type = FacetType.SINGLEVALUE;
        }
        */
        else if ( facetType.equals("set") || facetType.equals("list") ) {
            values.add(facetValue);
            type = FacetType.MULTISELECT;
        }
        else {
            values.add(facetValue);
            type = FacetType.SINGLEVALUE;
        }
    }

    // TODO: Keep this functionality within the connector itself. Should not be part of the DXA module
    private static String formatRangeValue(String rangeValue) {
        if ( FH_BAD_FLOAT_PATTERN.matcher(rangeValue).matches() ) {
            float floatValue = Float.parseFloat(rangeValue);
            return String.format(java.util.Locale.US,"%.2f", floatValue);
        }
        return rangeValue;
    }

}
