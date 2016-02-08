<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.BreadcrumbWidget" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<div>
    <ol class="breadcrumb" ${markup.entity(entity)}>
        <li>
            <a href="javascript:history.back(-1)"><i class="fa fa-chevron-left"></i></a>
        </li>
        <li class="detail-home-nav">
            <%-- TODO: Have this more configurable and respect the localization paths --%>
            <a href="/"><i class="fa fa-home"><span class="sr-only">Home</span></i></a>
        </li>
        <c:forEach var="breadcrumb" items="${entity.breadcrumbs}">
            <c:if test="${breadcrumb.category == true}">
                    <li>
                        <a href="${breadcrumb.url}">${breadcrumb.title}</a>
                    </li>
            </c:if>
        </c:forEach>
    </ol>

    <%--
    <div class="active-facets">
        <c:forEach var="breadcrumb" items="${entity.breadcrumbs}">
            <c:if test="${breadcrumb.category == false}">
                <button type="button" class="btn btn-lg btn-primary" disabled="disabled">${breadcrumb.title}</button>
            </c:if>
        </c:forEach>
    </div>
    --%>
</div>