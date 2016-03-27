<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.BreadcrumbWidget" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="localization" type="com.sdl.webapp.common.api.localization.Localization" scope="request"/>
<div>
    <ol class="breadcrumb" ${markup.entity(entity)}>
        <li>
            <a href="${localization.localizePath('/')}"><i class="fa fa-home"><span class="sr-only">Home</span></i></a>
        </li>
        <c:forEach var="breadcrumb" items="${entity.breadcrumbs}">
            <c:choose>
                <c:when test="${breadcrumb.category == true && breadcrumb.title == fhCategory.name}">
                    <li class="active">
                        ${breadcrumb.title}
                    </li>
                </c:when>
                <c:when test="${breadcrumb.category == true}">
                    <li>
                        <a href="${breadcrumb.url}">${breadcrumb.title}</a>
                    </li>
                </c:when>
            </c:choose>
        </c:forEach>
        <li class="active item-count">(${entity.totalItems})</li>
    </ol>

    <div class="active-facets">
        <c:forEach var="breadcrumb" items="${entity.breadcrumbs}">
            <c:if test="${breadcrumb.category == false}">
                <a href="${breadcrumb.url}" class="btn btn-primary">${breadcrumb.title} <i class="fa fa-times"></i></a>
            </c:if>
        </c:forEach>
    </div>
</div>
