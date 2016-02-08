<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="promotion" type="com.sdl.ecommerce.api.model.ContentPromotion" scope="request"/>
<jsp:useBean id="viewHelper" type="com.sdl.ecommerce.dxa.ECommerceViewHelper" scope="request"/>
<div>
    <div style="position: relative;">
        <c:if test="${viewHelper.showEditControls(promotion)}">
            <a class="xpm-button popup-iframe" style="margin: 8px 8px; position: absolute;background-color: transparent;" href="${promotion.editUrl}"><i class="fa fa-pencil-square"></i></a>
        </c:if>
    </div>
    <a href="${promotion.link}">
        <c:choose>
            <c:when test="${not empty promotion.imageUrl}">
                <div class="hero">
                    <img src="${promotion.imageUrl}" />
                    <c:if test="${promotion.title != null or promotion.text != null}">
                        <div class="overlay overlay-tl ribbon">
                            <c:if test="${promotion.title}">
                                <h1>${promotion.title}</h1>
                            </c:if>
                            <c:if test="${promotion.text}">
                                 ${promotion.text}
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <article class="rich-text">
                    <c:if test="${promotion.title}">
                        <h1>${promotion.title}</h1>
                    </c:if>
                    <c:if test="${promotion.text}">
                        <div class="content">
                           ${promotion.text}
                        </div>
                    </c:if>
                </article>
            </c:otherwise>
        </c:choose>
    </a>
</div>
