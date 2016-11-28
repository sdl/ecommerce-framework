<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.PromotionsWidget" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<div>
    <c:forEach var="promotion" items="${entity.promotions}" varStatus="status">
        <c:if test="${entity.maxPromotions == null or status.index lt entity.maxPromotions }">
            <div>
                <div class="row">
                    <div class="col-sm-12">
                        <c:set var="promotion" value="${promotion}" scope="request"/>
                        <c:import url="/WEB-INF/Views/ECommerce/Entity/Partials/${entity.getViewName(promotion)}.jsp"/>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>