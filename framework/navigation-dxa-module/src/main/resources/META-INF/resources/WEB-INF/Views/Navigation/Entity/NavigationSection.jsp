<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.webapp.navigation.model.NavigationSection" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>

<c:choose>
    <c:when test="${entity.items == null or entity.items.size() == 0}">
        <li>
            <a href="${entity.mainItem.link.url}" ${markup.property(entity, "mainItem")}>${entity.mainItem.link.linkText}</a>
        </li>
    </c:when>
    <c:otherwise>
        <li class="mega-nav-link">
            <a href="${entity.mainItem.link.url}" ${markup.property(entity, "mainItem")}>${entity.mainItem.link.linkText}</a>
            <div class="mega-nav">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="mega-nav-content">
                            <div class="row">
                                <c:forEach var="col" items="${entity.itemColumns}" varStatus="colCount">
                                    <div class="col-sm-6">
                                        <ul class="list-unstyled">
                                            <c:forEach var="item" items="${col}" varStatus="count">
                                                <li ${markup.property(entity, "items", (colCount.count * count.count)-1)}>
                                                    <a href="${item.link.url}">${item.link.linkText}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </li>
    </c:otherwise>
</c:choose>
