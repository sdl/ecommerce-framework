package com.sdl.ecommerce.fredhopper;

import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.LocalizationService;
import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.api.ProductQueryService;
import com.sdl.ecommerce.api.QueryFilterAttribute;
import com.sdl.ecommerce.api.model.*;
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
            FredhopperProduct fhItem = new FredhopperProduct(item.getId(), this.linkManager, this.productModelMappings);
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

    protected List<Breadcrumb> getBreadcrumbs(Universe universe, String urlPrefix, String rootTitle, List<FacetParameter> currentFacets) {

        // TODO: Decouple the category handling more from the breadcrumb so any kind of catagory implementation can be plugged in....

        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        if ( rootTitle != null ) {
            breadcrumbs.add(new FredhopperBreadcrumb(rootTitle, urlPrefix, true));
        }
        List<Crumb> fhCrumbs = universe.getBreadcrumbs().getCrumb();
        String categoryPath = urlPrefix + "/";
        for ( Crumb fhCrumb : fhCrumbs ) {
            // Category
            //
            if ( fhCrumb.getName().getAttributeType() != null && fhCrumb.getName().getAttributeType().equals("categories") ) {
                String path = FredhopperCategory.getPathName(fhCrumb.getName().getValue());
                categoryPath += path + "/";
                Breadcrumb breadcrumb = new FredhopperBreadcrumb(fhCrumb.getName().getValue(), categoryPath, true);
                breadcrumbs.add(breadcrumb);
            }
            // Facet
            //
            else if ( fhCrumb.getName().getAttributeType() != null ) {

                if ( fhCrumb.getRange() != null && fhCrumb.getRange().getValueSet().size() == 2 && fhCrumb.getRange().getValueSet().get(0).getAggregation() == AggregationType.AND ) {
                    com.fredhopper.webservice.client.Value minValue = fhCrumb.getRange().getValueSet().get(0).getEntry().get(0).getValue();
                    com.fredhopper.webservice.client.Value maxValue = fhCrumb.getRange().getValueSet().get(1).getEntry().get(0).getValue();
                    String facetTitle = minValue.getValue() + " - " + maxValue.getValue();
                    String facetValue = minValue.getNonMl() + "-" + maxValue.getNonMl();
                    String path = FredhopperFacet.getRemoveFacetLink(fhCrumb.getName().getAttributeType(), facetValue, currentFacets);
                    Breadcrumb breadcrumb = new FredhopperBreadcrumb(facetTitle, path, false);
                    breadcrumbs.add(breadcrumb);
                }
                // Aggregated facet values
                //
                else if ( fhCrumb.getRange() != null && fhCrumb.getRange().getValueSet().size() > 0 && fhCrumb.getRange().getValueSet().get(0).getAggregation() == AggregationType.OR ) {
                    StringBuilder aggregatedValue = new StringBuilder();
                    List<Entry> entries = fhCrumb.getRange().getValueSet().get(0).getEntry();
                    for ( int i=0; i < entries.size(); i++ ) {
                        aggregatedValue.append(entries.get(i).getValue().getNonMl());
                        if ( i < entries.size()-1 ) {
                            aggregatedValue.append(";");
                        }
                    }
                    String title;
                    Filtersection section = this.getSelectedFilterSectionWithValue(universe, aggregatedValue.toString());
                    if ( section != null ) {
                        title = section.getLink().getName();
                    }
                    else {
                        // If no aggregated title is found -> fall back on the first entry value
                        //
                        title = entries.get(0).getValue().getValue();
                    }
                    String path = FredhopperFacet.getRemoveFacetLink(fhCrumb.getName().getAttributeType(), aggregatedValue.toString(), currentFacets);
                    Breadcrumb breadcrumb = new FredhopperBreadcrumb(title, path, false);
                    breadcrumbs.add(breadcrumb);
                }
                else {
                    StringTokenizer valueTokenizer = new StringTokenizer(fhCrumb.getName().getNonMlValue(), "{};");
                    StringTokenizer titleTokenizer = new StringTokenizer(fhCrumb.getName().getValue(), ";");
                    while (valueTokenizer.hasMoreTokens()) {
                        String facetValue = valueTokenizer.nextToken();
                        String facetTitle = titleTokenizer.nextToken().trim();
                        String path = FredhopperFacet.getRemoveFacetLink(fhCrumb.getName().getAttributeType(), facetValue, currentFacets);
                        Breadcrumb breadcrumb = new FredhopperBreadcrumb(facetTitle, path, false);
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

    protected List<FacetGroup> getFacetGroups(Universe universe, List<FacetParameter> currentFacets, String urlPrefix) {
        return this.getFacetGroups(universe, currentFacets, urlPrefix, null, null);
    }

    protected List<FacetGroup> getFacetGroups(Universe universe, List<FacetParameter> currentFacets, String urlPrefix, String categoryUrlPrefix, List<QueryFilterAttribute> queryFilterAttributes) {
        List<Filter> filters = this.getFacetFilters(universe);
        List<FacetGroup> facetGroups = new ArrayList<>();
        String facetPrefix = "";
        if ( categoryUrlPrefix != null ) {
            facetPrefix = categoryUrlPrefix;
        }
        for ( Filter filter : filters ) {
            if ( !include(filter.getCustomFields(), queryFilterAttributes) ) {
                continue;
            }
            String editUrl = "/fh-edit/facets.fh?" +  URLDecoder.decode(universe.getLink().getUrlParams() + "&id=" + filter.getFacetid());
            FacetGroup facetGroup = new FredhopperFacetGroup(filter, editUrl);
            facetGroups.add(facetGroup);

            // TODO: Get category by id here on the leaf entry
            for (Filtersection section : filter.getFiltersection()) {
                boolean isSelected = section.isSelected() != null && section.isSelected();
                String link = "";
                if ( facetGroup.isCategory() ) {
                    Category category = this.categoryService.getCategoryById(section.getValue().getValue());
                    if ( category != null ) {

                        link = category.getCategoryLink(urlPrefix);
                        link += FredhopperFacet.getFacetLink(currentFacets);
                    }
                }
                else { // facet

                    // If a hidden facet value -> ignore to add it to the facet group
                    //
                    if ( this.hiddenFacetValues != null && this.hiddenFacetValues.contains(section.getLink().getName()) ) {
                        continue;
                    }

                    String facetValue = section.getValue().getValue();

                    if (isSelected) {
                        link = facetPrefix + FredhopperFacet.getRemoveFacetLink(filter.getOn(), facetValue, currentFacets);
                    } else {
                        link = facetPrefix + FredhopperFacet.getAddFacetLink(filter.getOn(), facetValue, filter.getBasetype().value(), currentFacets);
                    }

                }
                Facet facet = new FredhopperFacet(section.getLink().getName(), link, section.getNr(), isSelected);

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
