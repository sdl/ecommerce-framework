package com.sdl.ecommerce.service.rest;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.ecommerce.api.model.Product;
import com.sdl.ecommerce.service.model.ErrorMessage;
import com.sdl.ecommerce.service.model.RestProduct;
import com.sdl.ecommerce.service.model.RestQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.StringTokenizer;

/**
 * Product Controller
 *
 * @author nic
 */
@RestController
@RequestMapping("/ecommerce.svc/rest/v1//product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private LocalizationService localizationService;

    @RequestMapping("/query")
    public QueryResult query(@RequestParam(required = false) String categoryId,
                             @RequestParam(required = false) String searchPhrase,
                             @RequestParam(required = false) String facets,
                             @RequestParam(required = false) Integer startIndex,
                             @RequestParam(required = false) Integer viewSize,
                             @RequestParam(required = false) String viewType) throws ECommerceException {

        // Build E-Commerce query based on input query parameters
        //
        Query query = this.productQueryService.newQuery();
        if ( categoryId != null ) {
            Category category = this.categoryService.getCategoryById(categoryId);
            query.category(category);
        }
        if ( searchPhrase != null ) {
            query.searchPhrase(searchPhrase);
        }
        if ( facets != null ) {
            StringTokenizer tokenizer = new StringTokenizer(facets, "=&");
            while ( tokenizer.hasMoreTokens() ) {
                String facetName = tokenizer.nextToken();
                String facetValues = tokenizer.nextToken();
                FacetParameter facet = new FacetParameter(facetName, facetValues);
                query.facet(facet);
            }
        }
        if ( startIndex != null ) {
            query.startIndex(startIndex);
        }
        if ( viewSize != null ) {
            query.viewSize(viewSize);
        }
        if ( viewType != null ) {
            query.viewType(ViewType.valueOf(viewType));
        }

        // Consume system facets which will be handled as hidden facets in the query and will not be shown for the end-user
        //
        String systemFacets = this.localizationService.getLocalizedConfigProperty("system-facets");
        if ( systemFacets != null ) {
            StringTokenizer tokenizer = new StringTokenizer(systemFacets, "=&");
            while ( tokenizer.hasMoreTokens() ) {
                String facetName = tokenizer.nextToken();
                String facetValues = tokenizer.nextToken();
                FacetParameter facet = new FacetParameter(facetName + "_hidden", facetValues);
                query.facet(facet);
            }
        }


        QueryResult queryResult = this.productQueryService.query(query);
        return new RestQueryResult(queryResult);
    }

    @RequestMapping("/{productId}")
    public ResponseEntity get(@PathVariable String productId, @RequestParam Map<String,String> variantAttributes) throws ECommerceException {

        ProductDetailResult result;
        if (variantAttributes != null && variantAttributes.size() > 0) {
           result = this.productDetailService.getDetail(productId, variantAttributes);
        }
        else {
            result = this.productDetailService.getDetail(productId);
        }

        if ( result.getProductDetail() == null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Product with ID '" + productId + "' was not found"));
        }
        return new ResponseEntity<Product>(new RestProduct(result), HttpStatus.OK);
    }

}
