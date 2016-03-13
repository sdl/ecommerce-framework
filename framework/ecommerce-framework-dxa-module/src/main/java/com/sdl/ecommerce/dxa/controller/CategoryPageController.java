package com.sdl.ecommerce.dxa.controller;

import com.sdl.ecommerce.api.*;
import static com.sdl.ecommerce.dxa.ECommerceRequestAttributes.*;

import com.sdl.ecommerce.api.model.Category;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.api.content.PageNotFoundException;
import com.sdl.webapp.common.api.model.MvcData;
import com.sdl.webapp.common.api.model.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Category Page Controller
 *
 * @author nic
 */
@Controller
@RequestMapping("/c")
public class CategoryPageController extends AbstractECommercePageController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryPageController.class);

    static final String STORE_TITLE = "Store"; // TODO: Fetch this from the breadcrumb info instead!!!

    @RequestMapping(method = RequestMethod.GET, value = "/**", produces = {MediaType.TEXT_HTML_VALUE})
    public String handleCategoryPage(HttpServletRequest request, HttpServletResponse response) throws ContentProviderException {


        final String requestPath = webRequestContext.getRequestPath().replaceFirst("/c", "");
        final Category category = this.categoryService.getCategoryByPath(requestPath);
        final List<FacetParameter> facets = this.getFacetParametersFromRequestMap(request.getParameterMap());
        final PageModel templatePage = resolveTemplatePage(request, this.getSearchPath(requestPath, category));

        final Query query = this.queryService.newQuery();
        this.getQueryContributions(templatePage, query);

        final QueryResult result = this.queryService.query(query.
                category(category).
                facets(facets).
                startIndex(this.getStartIndex(request)));

        if ( result != null ) {

            if ( result.getRedirectUrl() != null ) {
                return "redirect:" + result.getRedirectUrl();
            }

            request.setAttribute(CATEGORY, category);
            request.setAttribute(RESULT, result);
            request.setAttribute(FACETS, facets);
            request.setAttribute(URL_PREFIX, "/c");

            // TODO: Fix show store link option
            //if ( Boolean.TRUE.equals(queryConfiguration.isShowStoreLink()) ) {
            //    request.setAttribute(ROOT_CATEGORY_TITLE, STORE_TITLE);
            //}


            if ( category != null ) {
                templatePage.setTitle(category.getName());
            }
            else {
                templatePage.setTitle(STORE_TITLE); // Root category
            }

            final MvcData mvcData = templatePage.getMvcData();
            return this.viewResolver.resolveView(mvcData, "Page", request);
        }

        throw new PageNotFoundException("Category page not found.");
    }

    protected List<String> getSearchPath(String url, Category category) {
        List<String> searchPath = new ArrayList<>();
        String categoryPath = "/categories/"; // TODO: Have this configurable. Should this be placed in System instead?
        Category currentCategory = category;
        while ( currentCategory != null ) {
            searchPath.add(categoryPath + category.getId());
            currentCategory = currentCategory.getParent();
        }
        StringTokenizer tokenizer = new StringTokenizer(url, "/");
        while ( tokenizer.hasMoreTokens() ) {
            categoryPath += tokenizer.nextToken();
            searchPath.add(0, categoryPath);
            if ( tokenizer.hasMoreTokens() ) {
                categoryPath += "-";
            }
        }
        searchPath.add("/categories/generic");
        return searchPath;
    }


}

