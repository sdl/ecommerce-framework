package com.sdl.ecommerce.dxa.controller;

import com.sdl.ecommerce.api.*;
import com.sdl.ecommerce.api.model.FacetParameter;
import com.sdl.webapp.common.api.MediaHelper;
import com.sdl.webapp.common.api.WebRequestContext;
import com.sdl.webapp.common.api.content.ContentProvider;
import com.sdl.webapp.common.api.content.ContentProviderException;
import com.sdl.webapp.common.api.content.PageNotFoundException;
import com.sdl.webapp.common.api.localization.Localization;
import com.sdl.webapp.common.api.model.EntityModel;
import com.sdl.webapp.common.api.model.PageModel;
import com.sdl.webapp.common.api.model.RegionModel;
import com.sdl.webapp.common.controller.BaseController;
import com.sdl.webapp.common.controller.exception.NotFoundException;
import com.sdl.webapp.common.markup.Markup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sdl.webapp.common.controller.ControllerUtils.NOT_FOUND_ERROR_VIEW;
import static com.sdl.webapp.common.controller.RequestAttributeNames.*;
import static com.sdl.webapp.common.controller.RequestAttributeNames.CONTEXTENGINE;
import static com.sdl.webapp.common.controller.RequestAttributeNames.MARKUP;

/**
 * AbstractECommercePageController
 *
 * @author nic
 */
public class AbstractECommercePageController extends BaseController {

    @Autowired
    protected ProductCategoryService categoryService;

    @Autowired
    protected ProductQueryService queryService;

    @Autowired
    protected WebRequestContext webRequestContext;

    @Autowired
    protected ContentProvider contentProvider;

    @Autowired
    private Markup markup;

    @Autowired
    private MediaHelper mediaHelper;

    /**
     * Resolve template page model based on provided search path
     * @param request
     * @param searchPath
     * @return page model
     * @throws ContentProviderException
     */
    protected PageModel resolveTemplatePage(HttpServletRequest request, List<String> searchPath) throws ContentProviderException {

        final Localization localization = webRequestContext.getLocalization();
        PageModel templatePage = null;
        for (String templatePagePath : searchPath) {
            try {
                templatePage = contentProvider.getPageModel(templatePagePath, localization);
            } catch (PageNotFoundException e) {
            }
            if (templatePage != null) {
                break;
            }
        }
        if (templatePage != null) {
            enrichRequest(request, templatePage, localization);
        }
        return templatePage;
    }

    protected List<FacetParameter> getFacetParametersFromRequestMap(Map<String, String[]> requestParameters) {

        List<FacetParameter> facetParameters = new ArrayList<>();
        for ( String parameterName : requestParameters.keySet() ) {
            // TODO: Use a global facet map here instead to validate against
            if ( !parameterName.equals("q") && !parameterName.equals("startIndex") ) {
                facetParameters.add(new FacetParameter(parameterName, requestParameters.get(parameterName)[0]));
            }
        }
        return facetParameters;
    }

    protected void enrichRequest(HttpServletRequest request, PageModel templatePage, Localization localization) {
        request.setAttribute(PAGE_ID, templatePage.getId());
        request.setAttribute(PAGE_MODEL, templatePage);
        request.setAttribute(LOCALIZATION, localization);
        request.setAttribute(MARKUP, markup);
        request.setAttribute(MEDIAHELPER, mediaHelper);
        request.setAttribute(SCREEN_WIDTH, mediaHelper.getScreenWidth());
        request.setAttribute(SOCIALSHARE_URL, webRequestContext.getFullUrl());
        request.setAttribute(CONTEXTENGINE, webRequestContext.getContextEngine());
    }

    protected int getStartIndex(HttpServletRequest request) {
        int startIndex = 0;
        String startIndexStr = request.getParameter("startIndex");
        if (startIndexStr != null) {
            startIndex = Integer.parseInt(startIndexStr);
        }
        return startIndex;
    }

    protected void getQueryContributions(PageModel templatePage, Query query) {

        for ( RegionModel region : templatePage.getRegions() ) {
            if ( !region.getName().equals("Header") && !region.getName().equals("Footer") ) {
                for (EntityModel entity : region.getEntities()) {
                    if (entity instanceof QueryInputContributor) {
                        ((QueryInputContributor) entity).contributeToQuery(query);
                    }
                }
            }
        }
    }

    /**
     * Handles a {@code NotFoundException}.
     *
     * @param request The request.
     * @return The name of the view that renders the "not found" page.
     */
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(HttpServletRequest request) {
        request.setAttribute(MARKUP, markup);
        // TODO: Show some predefined promos etc here???
        return NOT_FOUND_ERROR_VIEW;
    }
}