package com.sdl.ecommerce.fredhopper;

import com.fredhopper.webservice.client.*;
import com.sdl.ecommerce.api.ProductDetailResult;
import com.sdl.ecommerce.api.model.*;
import com.sdl.ecommerce.api.model.impl.GenericBreadcrumb;
import com.sdl.ecommerce.api.model.impl.GenericProductVariantAttribute;
import com.sdl.ecommerce.api.model.impl.GenericProductVariantAttributeType;
import com.sdl.ecommerce.api.model.impl.GenericProductVariantAttributeValueType;
import com.sdl.ecommerce.fredhopper.model.FredhopperBreadcrumb;
import com.sdl.ecommerce.fredhopper.model.FredhopperProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fredhopper Detail Result
 *
 * @author nic
 */
public class FredhopperDetailResult extends FredhopperResultBase implements ProductDetailResult {

    private Product productDetail = null;

    public FredhopperDetailResult(Page fredhopperPage, FredhopperLinkManager linkManager) {
        super(fredhopperPage, linkManager);
    }

    @Override
    public Product getProductDetail() {
        if ( this.productDetail == null ) {
            synchronized (this) {
                if (this.productDetail == null) {
                    this.productDetail = this.getProductDetail(this.universe);
                    // TODO: Throw a NOT_FOUND exception here!! Or have some kind of status code here???
                }
            }
        }
        return this.productDetail;
    }

    @Override
    public List<Breadcrumb> getBreadcrumbs() {
        return this.getProductBreadcrumbs(this.getProductDetail());
    }

    @Override
    public List<Promotion> getPromotions() {
        return this.getPromotions(this.universe);
    }

    private Product getProductDetail(Universe universe) {
        if ( universe.getItemsSection() != null && universe.getItemsSection().getItems() != null ) {
            List<Product> products = this.getProducts(universe.getItemsSection().getItems().getItem());
            if (products.size() > 0) {
                FredhopperProduct product = (FredhopperProduct) products.get(0);
                // TODO: categoryId should also be an model attribute, right?
                List<String> categoryIds = (List<String>) product.getAttribute("categoryId");
                if (categoryIds != null) {
                    for (String categoryId : categoryIds) {
                        Category category = this.categoryService.getCategoryById(categoryId);
                        if (category != null) {
                            product.getCategories().add(category);
                        }
                    }
                }
                this.setVariantInfo(product, universe);
                return product;
            }
        }
        return null;
    }

    private List<Breadcrumb> getProductBreadcrumbs(Product product) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();

        if ( product.getCategories() != null && product.getCategories().size() > 0 ) {
            Category category = product.getCategories().get(0);
            while (category != null) {
                breadcrumbs.add(0, new GenericBreadcrumb(category.getName(), new CategoryRef(category)));
                category = category.getParent();
            }
        }
        return breadcrumbs;
    }

    private void setVariantInfo(FredhopperProduct product, Universe universe) {

        // Get variant attribute types
        //
        List<ProductVariantAttributeType> attributeTypes = new ArrayList<>();
        List<Filter> filters = this.getFacetFilters(universe);
        for ( Filter filter : filters ) {
            // TODO: Have the variant prefixes configurable
            if ( filter.getOn().startsWith("variant_") ) {
                List<ProductVariantAttributeValueType> values = new ArrayList<>();
                for (Filtersection section : filter.getFiltersection()) {
                   values.add(new GenericProductVariantAttributeValueType(section.getValue().getValue(), section.getLink().getName(), section.isSelected() != null ? section.isSelected() : false));
                }
                attributeTypes.add(new GenericProductVariantAttributeType(filter.getOn(), filter.getTitle(), values));
            }
        }
        if ( attributeTypes.isEmpty() ) {
            // Current product has no variants
            //
            return;
        }
        product.setVariantAttributeTypes(attributeTypes);

        // Get current variant values
        //
        List<ProductVariantAttribute> variantAttributes = new ArrayList<>();
        for ( ProductVariantAttributeType attributeType : attributeTypes ) {
            Attribute attributeValue = product.getFredhopperAttribute(attributeType.getId());
            if ( !attributeValue.getValue().isEmpty() ) {
                Value value = attributeValue.getValue().get(0);
                variantAttributes.add(new GenericProductVariantAttribute(attributeType.getId(), attributeType.getName(), value.getNonMl(), value.getValue()));
            }
        }
        product.setVariantAttributes(variantAttributes);

    }

}
