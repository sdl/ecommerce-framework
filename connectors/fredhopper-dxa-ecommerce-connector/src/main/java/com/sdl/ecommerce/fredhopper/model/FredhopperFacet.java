package com.sdl.ecommerce.fredhopper.model;

import com.sdl.ecommerce.api.model.Facet;
import com.sdl.ecommerce.api.model.FacetParameter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fredhopper Facet
 *
 * @author nic
 */
public class FredhopperFacet implements Facet {

    private String title;
    private String url;
    private int count;
    private boolean isSelected;

    static private Pattern FH_RANGE_PATTERN = Pattern.compile("([0-9]+[\\.0-9]*)<[A-za-z_0-9]+<([0-9]+[\\.0-9]*)");
    static private Pattern FH_LESS_THAN_PATTERN = Pattern.compile("[A-za-z_0-9]+<([0-9]+[\\.0-9]*)");
    static private Pattern FH_GREATER_THAN_PATTERN = Pattern.compile("[A-za-z_0-9]+>([0-9]+[\\.0-9]*)");
    static private Pattern FH_BAD_FLOAT_PATTERN = Pattern.compile("[0-9]+\\.[0-9][0-9][0-9]+");

    public FredhopperFacet(String title, String url, int count, boolean isSelected) {
        this.title = title;
        this.url = url;
        this.count = count;
        this.isSelected = isSelected;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public static String getFacetLink(List<FacetParameter> currentFacets) {
        if ( currentFacets == null || currentFacets.size() == 0 ) { return ""; }
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        for ( FacetParameter facet : currentFacets ) {
            if ( firstParam ) {
                sb.append("?");
                firstParam = false;
            }
            else {
                sb.append("&");
            }
            sb.append(facet.toUrl());
        }
        return sb.toString();
    }

    public static String getAddFacetLink(String facetId, String facetValue, String facetType, List<FacetParameter> currentFacets) {

        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        boolean foundFacet = false;
        if ( currentFacets != null ) {
            for (FacetParameter facet : currentFacets) {
                if (firstParam) {
                    sb.append("?");
                    firstParam = false;
                } else {
                    sb.append("&");
                }
                if (facet.getName().equals(facetId) && !facet.containsValue(facetValue)) {
                    sb.append(facet.addValueToUrl(facetValue));
                    foundFacet = true;
                } else {
                    sb.append(facet.toUrl());
                }
            }
        }
        if ( !foundFacet ) {
            if ( firstParam ) {
                sb.append("?");
            }
            else {
                sb.append("&");
            }
            sb.append(getFacetUrl(facetId, facetValue, facetType));
        }
        return sb.toString();
    }

    public static String getRemoveFacetLink(String facetId, String facetValue, List<FacetParameter> currentFacets) {

        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        if ( currentFacets != null ) {
            for (FacetParameter facet : currentFacets) {
                if (firstParam) {
                    sb.append("?");
                    firstParam = false;
                } else {
                    sb.append("&");
                }
                if (facet.getName().equals(facetId) && facet.containsValue(facetValue)) {
                    sb.append(facet.removeValueToUrl(facetValue));
                } else {
                    sb.append(facet.toUrl());
                }
            }
        }
        return sb.toString();
    }

    private static String getFacetUrl(String facetId, String facetValue, String facetType) {
        String name = facetId;
        String value = facetValue;
        Matcher rangeMatcher = FH_RANGE_PATTERN.matcher(facetValue);
        Matcher ltMatcher = FH_LESS_THAN_PATTERN.matcher(facetValue);
        Matcher gtMatcher = FH_GREATER_THAN_PATTERN.matcher(facetValue);

        if ( rangeMatcher.matches() ) {
            String min = formatRangeValue(rangeMatcher.group(1));
            String max = formatRangeValue(rangeMatcher.group(2));
            value = min + "-" + max;
        }
        else if ( ltMatcher.matches() ) {
            value = "<" +ltMatcher.group(1);
        }
        else if ( gtMatcher.matches() ) {
            value = ">" + gtMatcher.group(1);
        }
        else if ( facetType.equals("float") || facetType.equals("int") || facetType.equals("text") ) {
            name += "_val";
        }

        return name + "=" + value;
    }

    private static String formatRangeValue(String rangeValue) {
        if ( FH_BAD_FLOAT_PATTERN.matcher(rangeValue).matches() ) {
            float floatValue = Float.parseFloat(rangeValue);
            return String.format(java.util.Locale.US,"%.2f", floatValue);
        }
        return rangeValue;
    }
}
