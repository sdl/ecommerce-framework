<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.ProductDetailWidget" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="screenWidth" type="com.sdl.webapp.common.api.ScreenWidth" scope="request"/>

<div class="content" style="margin-bottom: 8px;">
    <div class="row">
        <div class="col-md-6" ${markup.property(entity, "image")}>
            <c:choose>
                <c:when test="${not empty entity.image && entity.image.fileSize gt 100}">
                    <img class="center-block teaser-img" width="100%" id="product-image" src="${entity.image.url}"/>
                </c:when>
                <c:when test="${not empty entity.product.primaryImageUrl}">
                    <xpm:if-enabled>
                    <!-- Start Component Field: {"XPath":"tcm:Content/custom:ProductDetailWidget/custom:image[1]"} -->
                    </xpm:if-enabled>
                    <img class="center-block teaser-img" width="100%" id="product-image" src="${entity.product.primaryImageUrl}"/>
                </c:when>
            </c:choose>
        </div>
        <div class="col-md-6">
            <h3 class="product-title" ${markup.property(entity, "title")}>
                <c:choose>
                    <c:when test="${not empty entity.title}">
                       ${entity.title}
                    </c:when>
                    <c:otherwise>
                        <xpm:if-enabled>
                            <!-- Start Component Field: {"XPath":"tcm:Content/custom:ProductDetailWidget/custom:title[1]"} -->
                        </xpm:if-enabled>
                        ${entity.product.name}
                    </c:otherwise>
                </c:choose>
            </h3>
            <div class="product-description">
                <div>
                    <div ${markup.property(entity, "description")}>
                        <c:choose>
                            <c:when test="${not empty entity.description}">
                                ${entity.description}
                            </c:when>
                            <c:otherwise>
                                <xpm:if-enabled>
                                    <!-- Start Component Field: {"XPath":"tcm:Content/custom:ProductDetailWidget/custom:description[1]"} -->
                                </xpm:if-enabled>
                                ${entity.product.description}
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <h3 class="center-block" style="margin-top: 16px;">
                        ${entity.product.price.formattedPrice}
                    </h3>
                </div>
                <div class="product-add-to-cart">
                    <a class="btn btn-primary add-to-cart-button" data-product-id="${entity.product.id}">Add to Cart <i
                            class="fa fa-cart-plus"></i></a>
                </div>
            </div>
        </div>
    </div>
</div>


