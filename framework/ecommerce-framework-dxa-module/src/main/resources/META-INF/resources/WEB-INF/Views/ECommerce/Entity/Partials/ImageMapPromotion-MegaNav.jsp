<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="promotion" type="com.sdl.ecommerce.api.model.ImageMapPromotion" scope="request"/>
<jsp:useBean id="viewHelper" type="com.sdl.ecommerce.dxa.ECommerceViewHelper" scope="request"/>
<jsp:useBean id="linkResolver" type="com.sdl.ecommerce.api.ECommerceLinkResolver" scope="request"/>
<div class="hero">
    <div style="position: relative;">
        <c:if test="${viewHelper.showEditControls(promotion)}">
            <a class="xpm-button popup-iframe" style="margin: 8px 8px; position: absolute;background-color: transparent;" href="${promotion.editUrl}"><i class="fa fa-pencil-square"></i></a>
        </c:if>
    <div>
    <img src="${promotion.imageUrl}" usemap="meganav-${promotion.id}">
    <map name="meganav-${promotion.id}">
        <c:forEach var="contentArea" items="${promotion.contentAreas}">
            <area shape="rect" coords="${contentArea.x1},${contentArea.y1},${contentArea.x2},${contentArea.y2}" href="${linkResolver.getLocationLink(contentArea.location)}">
        </c:forEach>
    </map>
</div>
