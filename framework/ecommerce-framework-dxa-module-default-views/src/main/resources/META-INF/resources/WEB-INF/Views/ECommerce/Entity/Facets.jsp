<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %> >
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.FacetsWidget" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="screenWidth" type="com.sdl.webapp.common.api.ScreenWidth" scope="request"/>
<jsp:useBean id="viewHelper" type="com.sdl.ecommerce.dxa.ECommerceViewHelper" scope="request"/>
<jsp:useBean id="linkResolver" type="com.sdl.ecommerce.api.ECommerceLinkResolver" scope="request"/>
<div>

    <%-- Temporary fix to get the in-context menu enabled. Should be replaced by a separate in-context menu bar in the top of the page --%>
    <c:if test="${viewHelper.hasBeenInvokedViaXpm()}">
        <c:import url="/Entity/InContextEditMenu.jsp"/>
    </c:if>

    <c:forEach var="facetGroup" items="${entity.facetGroups}">
            <h5 class="facet-group">${facetGroup.title} <c:if test="${viewHelper.showEditControls(facetGroup)}"><a class="xpm-button popup-iframe" style="margin: 0px 8px;" href="${facetGroup.editUrl}"><i class="fa fa-pencil-square"></i></a></c:if> </h5>
            <c:choose>
                <c:when test="${facetGroup.isCategory()}">
                    <ul class="category-facets">
                        <c:forEach var="facet" items="${facetGroup.facets}">
                            <li><a href="${linkResolver.getFacetLink(facet)}" class="facet-item">${facet.title}<c:if test="${facet.count gt 0}"> (${facet.count})</c:if></a></li>
                        </c:forEach>
                    </ul>

                </c:when>
                <c:otherwise>
                    <c:forEach var="facet" items="${facetGroup.facets}">
                        <c:set var="facetUrl" value="${linkResolver.getFacetLink(facet)}"/>
                        <a href="${facetUrl}" class="facet-link">
                            <div class="checkbox facet-item">
                                <label>
                                    <input onclick="window.location = this.getAttribute('data-facet-url');"
                                           type="checkbox" data-facet-url="${facetUrl}" <c:if test="${facet.selected}">checked="checked"</c:if>>${facet.title}<c:if test="${facet.count gt 0}"> (${facet.count})</c:if>
                                </label>
                            </div>
                        </a>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
    </c:forEach>
</div>