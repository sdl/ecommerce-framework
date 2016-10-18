package com.sdl.ecommerce.fredhopper;

import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.api.QueryFilterAttribute;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;
import com.sdl.ecommerce.api.model.impl.GenericFacet;
import com.sdl.ecommerce.fredhopper.model.*;
import com.sdl.ecommerce.fredhopper.model.promotion.FredhopperPromotion;
import static com.sdl.ecommerce.fredhopper.FredhopperHelper.*;

import java.net.URLDecoder;
import java.util.*;

/**
 * FredhopperResultBase
 *
 * @author nic
 */
public abstract class FredhopperResultBase {

    protected Page fredhopperPage;
    protected Universe universe;
    protected FredhopperLinkManager linkManager;
    protected ProductCategoryService categoryService;
    protected ProductQueryService queryService;
    protected LocalizationService localizationService;
    protected Map<String,String> productModelMappings;
    protected List<String> hiddenFacetValues;
    protected List<String> aggregatedFacets;

    protected FredhopperResultBase(Page fredhopperPage, FredhopperLinkManager linkManager) {
        this.fredhopperPage = fredhopperPage;
        this.universe = this.getUniverse(fredhopperPage);
        this.linkManager = linkManager;
    }

    /****** Setters to inject the different services needed for getting additional data from the result set *****/

    void setQueryService(ProductQueryService queryService) {
        this.queryService = queryService;
    }

    void setCategoryService(ProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setLocalizationService(LocalizationService localizationService) {
        this.localizationService = localizationService;
        this.productModelMappings = getProductModelMappings(this.localizationService);
        this.hiddenFacetValues = getHiddenFacetValues(this.localizationService);
        this.aggregatedFacets =  getAggregatedFacets(this.localizationService);

    }

    /****** Helper functions to extract data from the Fredhopper universe *******/

    protected Universe getUniverse(Page page) {
        if ( page.getUniverses() != null ) {
            for (Universe u : page.getUniverses().getUniverse()) {
                if (UniverseType.SELECTED.equals(u.getType())) {
                    return u;
                }
            }
        }
        return null;
    }

    protected List<Product> getProducts(List<com.fredhopper.webservice.client.Item> items) {
        List<Product> products = new ArrayList<>();
        for ( com.fredhopper.webservice.client.Item item : items ) {
            FredhopperProduct fhItem = new FredhopperProduct(item.getId(), this.linkManager, this.productModelMappings, this.localizationService);
            products.add(fhItem);
            for (Attribute attribute : item.getAttribute()) {
                String name = attribute.getName();
                Object value;
                if (attribute.getValue().size() == 0) {
                    continue;
                }
                String baseType = attribute.getBasetype().value();

                /*
                if ( baseType.equals("text") ) {
                    value = attribute.getValue().get(0).getValue();
                }
                else if (baseType.equals("int") ) {
                    value = Integer.parseInt(attribute.getValue().get(0).getValue());
                }
                else if (baseType.equals("float") ) {
                    value = Float.parseFloat(attribute.getValue().get(0).getValue());
                }
                */
                if (baseType.equals("set") || baseType.equals("list")) {
                    List<String> valueList = new ArrayList<>();
                    for (com.fredhopper.webservice.client.Value attrValue : attribute.getValue()) {
                        valueList.add(attrValue.getValue());
                    }
                    value = valueList;
                }
                else if (baseType.equals("cat")) {
                    name = "categoryId";
                    List<String> valueList = new ArrayList<>();
                    for (com.fredhopper.webservice.client.Value attrValue : attribute.getValue()) {
                        valueList.add(attrValue.getNonMl());
                    }
                    value = valueList;
                }
                else {
                    value = attribute.getValue().get(0).getValue();
                }
                fhItem.getAttributes().put(name, value);

                // Save the internal representation as well.
                // TODO: Merge this with the generic attribute representation
                //
                fhItem.addFredhopperlAttribute(name, attribute);
            }
        }
        return products;
    }

    protected List<Promotion> getPromotions(Universe universe) {
        return this.getPromotions(universe, null);
    }

    protected List<Promotion> getPromotions(Universe universe, List<QueryFilterAttribute> filterAttributes) {

        // For now just take the first theme list
        //
        if ( !universe.getThemes().isEmpty() ) {
            List<Theme> themes = universe.getThemes().get(0).getTheme();
            List<Promotion> promotions = new ArrayList<>();
            for ( Theme theme : themes ) {
                if ( !include(theme.getCustomFields(), filterAttributes) ) {
                    continue;
                }
                // TODO: How to handle edit urls in a micro service environment? Point to a proxy service?
                String editUrl = "/fh-edit/campaigns.fh?" + URLDecoder.decode(universe.getLink().getUrlParams()) + "&id=" + theme.getId();
                List<Product> products = null;
                if ( theme.getItems() != null ) {
                    products = this.getProducts(theme.getItems().getItem());
                }
                promotions.add(FredhopperPromotion.build(theme, this.linkManager, products, editUrl, this.categoryService));
            }
            return promotions;
        }
        return Collections.EMPTY_LIST;
    }

    protected List<Breadcrumb> getBreadcrumbs(Universe universe, List<FacetParameter> currentFacets) {

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        List<Crumb> fhCrumbs = universe.getBreadcrumbs().getCrumb();
        for ( Crumb fhCrumb : fhCrumbs ) {
            // Category
            //
            if (fhCrumb.getName().getAttributeType() != null && fhCrumb.getName().getAttributeType().equals("categories")) {
                String categoryId = fhCrumb.getName().getNonMlValue().replace("{", "").replace("}", "");
                Category category = categoryService.getCategoryById(categoryId);
                Breadcrumb breadcrumb = new GenericBreadcrumb(fhCrumb.getName().getValue(), new CategoryRef(category));
                breadcrumbs.add(breadcrumb);
            }
            // Facet
            //
            else if (fhCrumb.getName().getAttributeType() != null) {

                String facetId = fhCrumb.getName().getAttributeType();
                if (fhCrumb.getRange() != null && fhCrumb.getRange().getValueSet().size() == 2 && fhCrumb.getRange().getValueSet().get(0).getAggregation() == AggregationType.AND) {
                    com.fredhopper.webservice.client.Value minValue = fhCrumb.getRange().getValueSet().get(0).getEntry().get(0).getValue();
                    com.fredhopper.webservice.client.Value maxValue = fhCrumb.getRange().getValueSet().get(1).getEntry().get(0).getValue();
                    String facetTitle = minValue.getValue() + " - " + maxValue.getValue();
                    String facetValue = minValue.getNonMl() + "-" + maxValue.getNonMl();
                    Facet facet = new GenericFacet(facetId, facetTitle, facetValue, Facet.FacetType.RANGE);

                    Breadcrumb breadcrumb = new GenericBreadcrumb(facetTitle, facet);
                    breadcrumbs.add(breadcrumb);
                }
                // Aggregated facet values
                //
                // Temporary work-around to get most aggregated facets to work. Needs to be improved.
                //
                else if (this.aggregatedFacets != null && this.aggregatedFacets.contains(fhCrumb.getName().getAttributeType()) && fhCrumb.getRange() != null && fhCrumb.getRange().getValueSet().size() > 0 && fhCrumb.getRange().getValueSet().get(0).getAggregation() == AggregationType.OR) {
                    StringBuilder aggregatedValue = new StringBuilder();
                    List<Entry> entries = fhCrumb.getRange().getValueSet().get(0).getEntry();
                    for (int i = 0; i < entries.size(); i++) {
                        aggregatedValue.append(entries.get(i).getValue().getNonMl());
                        if (i < entries.size() - 1) {
                            aggregatedValue.append(";");
                        }
                    }
                    String title;
                    Filtersection section = this.getSelectedFilterSectionWithValue(universe, aggregatedValue.toString());
                    if (section != null) {
                        title = section.getLink().getName();
                    } else {
                        // If no aggregated title is found -> fall back on the first entry value
                        //
                        title = entries.get(0).getValue().getValue();
                    }
                    Facet facet = new GenericFacet(facetId, title, aggregatedValue.toString(), Facet.FacetType.MULTISELECT);
                    Breadcrumb breadcrumb = new GenericBreadcrumb(title, facet);
                    breadcrumbs.add(breadcrumb);
                } else {
                    StringTokenizer valueTokenizer = new StringTokenizer(fhCrumb.getName().getNonMlValue(), "{};");
                    StringTokenizer titleTokenizer = new StringTokenizer(fhCrumb.getName().getValue(), ";");
                    while (valueTokenizer.hasMoreTokens()) {
                        String facetValue = valueTokenizer.nextToken();
                        String facetTitle = titleTokenizer.nextToken().trim();
                        Facet facet = new GenericFacet(facetId, facetTitle, facetValue, Facet.FacetType.MULTISELECT);
                        Breadcrumb breadcrumb = new GenericBreadcrumb(facetTitle, facet);
                        breadcrumbs.add(breadcrumb);
                    }
                }
            }
        }
        return breadcrumbs;
    }

    protected Filtersection getSelectedFilterSectionWithValue(Universe universe, String value) {
        List<Filter> filters = this.getFacetFilters(universe);
        for ( Filter filter : filters ) {
            for ( Filtersection section : filter.getFiltersection() ) {
                if ( section.isSelected() != null && section.isSelected() && value.equals(section.getValue().getValue()) ) {
                    return section;
                }
            }
        }
        return null;
    }

    protected List<FacetGroup> getFacetGroups(Universe universe) {
        return this.getFacetGroups(universe, null);
    }

    protected List<FacetGroup> getFacetGroups(Universe universe, List<QueryFilterAttribute> queryFilterAttributes) {

        List<Filter> filters = this.getFacetFilters(universe);
        List<FacetGroup> facetGroups = new ArrayList<>();

        for ( Filter filter : filters ) {
            if ( !include(filter.getCustomFields(), queryFilterAttributes) ) {
                continue;
            }
            // TODO: Move edit URL generation to somewhere else!!
            String editUrl = "/fh-edit/facets.fh?" +  URLDecoder.decode(universe.getLink().getUrlParams() + "&id=" + filter.getFacetid());
            FacetGroup facetGroup = new FredhopperFacetGroup(filter, editUrl);
            facetGroups.add(facetGroup);

            for (Filtersection section : filter.getFiltersection()) {

                if ( !facetGroup.isCategory() ) { // facet

                    // If a hidden facet value -> ignore to add it to the facet group
                    //
                    if ( this.hiddenFacetValues != null && this.hiddenFacetValues.contains(section.getLink().getName()) ) {
                        continue;
                    }
                }
                Facet facet = new FredhopperFacet(filter, section);
                facetGroup.getFacets().add(facet);
            }
        }
        return facetGroups;
    }

    protected List<Filter> getFacetFilters(Universe universe) {
        List<Facetmap> facetmapArray = universe.getFacetmap();
        List<Filter> filters = new ArrayList<>();
        if ( facetmapArray != null ) {
            for (Facetmap facetmap : facetmapArray) {
                if (facetmap.getFilter() != null && !facetmap.getUniverse().equals("search")) {
                    filters.addAll(facetmap.getFilter());
                }
            }
        }
        return filters;
    }

    protected boolean include(CustomFields customFields, List<QueryFilterAttribute> filterAttributes) {
        if ( filterAttributes != null ) {
            for (QueryFilterAttribute filterAttribute : filterAttributes) {
                String customFieldValue = this.getCustomFieldValue(customFields, filterAttribute.getName());
                if (!filterAttribute.getValue().equals(customFieldValue) && filterAttribute.getMode() == QueryFilterAttribute.FilterMode.INCLUDE) {
                    return false;
                } else if (filterAttribute.getValue().equals(customFieldValue) && filterAttribute.getMode() == QueryFilterAttribute.FilterMode.EXCLUDE) {
                    return false;
                }
            }
        }
        return true;
    }

    protected String getCustomFieldValue(CustomFields customFields, String name) {
        if ( customFields != null ) {
            for (CustomField field : customFields.getCustomField()) {
                if (field.getName().equals(name)) {
                    return field.getValue();
                }
            }
        }
        return null;
    }

}
