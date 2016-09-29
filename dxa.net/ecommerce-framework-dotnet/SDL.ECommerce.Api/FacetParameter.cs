using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace SDL.ECommerce.Api
{
/**
 * Facet Parameter.
 * Is used for representing a user facing facet.
 *
 * @author nic
 */
public class FacetParameter {

    // TODO: Optimize the implementation to minimize the parsing of the facet _values
    // TODO: Combine this together with the Facet. This seems rather being an implementation of the facet for web implementations

    /**
     * Parameter type
     */
    public enum ParameterType {
        SINGLEVALUE,
        MULTISELECT,
        RANGE,
        LESS_THAN,
        GREATER_THAN
    }

    /*
    static private Pattern RANGE_PATTERN = Pattern.compile("([0-9]+[\\.0-9]*)\\-([0-9]+[\\.0-9]*)");
    static private Pattern LESS_THAN_PATTERN = Pattern.compile("<([0-9]+[\\.0-9]*)");
    static private Pattern GREATER_THAN_PATTERN = Pattern.compile(">([0-9]+[\\.0-9]*)");
    */
    private string _name;
    private List<string> _values = new List<string>();
    private ParameterType type;

    /**
     * Create new facet parameter
     * @param _name
     */
    public FacetParameter(string name) {
        _name = name;
    }

    /**
     * Create a new facet. The value is parsed to extract current type and _values.
     * @param _name
     * @param strValue
     */
    public FacetParameter(string name, string strValue) {
        _name = name;
        _values.Add(strValue); // TEMP FIX 
        type = ParameterType.MULTISELECT;     

        /*
        Matcher rangeMatcher = RANGE_PATTERN.matcher(strValue);
        Matcher lessThanMatcher = LESS_THAN_PATTERN.matcher(strValue);
        Matcher greaterThanMatcher = GREATER_THAN_PATTERN.matcher(strValue);

        if (name.EndsWith("_val") ) {
            this._name = this._name.Replace("_val", "");
            this.type = ParameterType.SINGLEVALUE;
            this._values.Add(strValue);
        }
        else if ( rangeMatcher.matches()) {
            this.type = ParameterType.RANGE;
            String min = rangeMatcher.group(1);
            String max = rangeMatcher.group(2);
            this._values.Add(min);
            this._values.Add(max);
        }
        else if ( lessThanMatcher.matches() ) {
            this.type = ParameterType.LESS_THAN;
            String value = lessThanMatcher.group(1);
            this._values.add(value);
        }
        else if ( greaterThanMatcher.matches() ) {
            this.type = ParameterType.GREATER_THAN;
            String value = greaterThanMatcher.group(1);
            this._values.add(value);
        }
        else  {
            // Multi-select
            this.type = ParameterType.MULTISELECT;
            StringTokenizer tokenizer = new StringTokenizer(strValue, "|");
            while (tokenizer.hasMoreTokens()) {
                _values.add(tokenizer.nextToken());
            }
        }
         */
    }

    /**
     * Name of the facet parameter. Is shown in the facet URL.
     * @return _name
     */
    public string Name() {
        return _name;
    }

    /**
     * Get all facet _values.
     * @return facet _values
     */
    public List<String> Values() {
        return _values;
    }

    /**
     * Check if specific value is included in this facet parameter (when having multi select facets).
     *
     * @param facetValue
     * @return contains value
     */
    public bool ContainsValue(string facetValue) {
        /*
        if ( this.type == ParameterType.RANGE ) {
            
            Matcher rangeMatcher = RANGE_PATTERN.matcher(facetValue);
            if (rangeMatcher.matches() && this._values.size() == 2) {
                String min = rangeMatcher.group(1);
                String max = rangeMatcher.group(2);
                return (this._values.get(0).equals(min) && this._values.get(1).equals(max));
            }
            return false;
        }
        else {
            return this._values.Contains(facetValue);
        }
        */
        return _values.Contains(facetValue);
    }

    /**
     * Get parameter type.
     * @return type
     */
    public ParameterType Type() {
        return type;
    }

    public static FacetParameter FromUrl(String facetUrl)
    {
        var urlBits = facetUrl.Split('=');
        if(urlBits.Length < 2){
            return null;
        }

        string name = urlBits[0];
        string strValue = urlBits[1];

        return new FacetParameter(name, strValue);
    }

    /**
     * Convert the facet parameter to URL format.
     * @return url fragment
     */
    public string ToUrl() {
        return AddValueToUrl(null);
    }

    /**
     * Convert the facet parameter to URL format.
     *
     * @param additionalValue
     * @return url fragment
     */
    public string AddValueToUrl(string additionalValue) {
        StringBuilder sb = new StringBuilder();
        sb.Append(this._name);
        if ( type == ParameterType.SINGLEVALUE ) {
            sb.Append("_val");
        }
        sb.Append("=");
        if ( type == ParameterType.LESS_THAN ) {
            sb.Append("<");
        }
        else if ( type == ParameterType.GREATER_THAN ) {
            sb.Append(">");
        }
        String delimiter = "";
        if (type == ParameterType.MULTISELECT) {
            delimiter = "|";
        } else if (type == ParameterType.RANGE) {
            delimiter = "-";
        }

        for ( int i=0; i < this._values.Count; i++ ) {
            sb.Append(_values[i]);
            if ( i < _values.Count()-1 ) {
                sb.Append(delimiter);
            }
        }
        if ( additionalValue != null && type == ParameterType.MULTISELECT ) {
            sb.Append(delimiter);
            sb.Append(additionalValue);
        }
        return sb.ToString();
    }

    /**
     * Remove a value from a facet URL fragment (used for breadcrumbs and de-selecting multi-value facets).
     * @param removedValue
     * @return url fragment
     */
    public String RemoveValueToUrl(String removedValue) {
        if ( ( _values.Count == 1 && _values[0].Equals(removedValue) ) ||
                ( type == ParameterType.RANGE && this._values.Count == 2 ) ) {
            return "";
        }
        var sb = new StringBuilder();
        sb.Append(this._name);
        if ( type == ParameterType.SINGLEVALUE ) {
            sb.Append("_val");
        }
        sb.Append("=");
        if ( type == ParameterType.LESS_THAN ) {
            sb.Append("<");
        }
        else if ( type == ParameterType.GREATER_THAN ) {
            sb.Append(">");
        }
        String delimiter = "";
        if (type == ParameterType.MULTISELECT) {
            delimiter = "|";
        } else if (type == ParameterType.RANGE) {
            delimiter = "-";
        }
        List<string> values = new List<string>();
        /*
        for ( string value : this._values ) {
            if ( type != ParameterType.MULTISELECT ||
                    (type == ParameterType.MULTISELECT && !value.equals(removedValue)) ) {
                values.add(value);
            }
        }
        */

        for ( int i=0; i < values.Count; i++ ) {
            String value = values[i];
            sb.Append(value);
            if ( i < values.Count-1 ) {
                sb.Append(delimiter);
            }
        }
        return sb.ToString();
    }

}
}
