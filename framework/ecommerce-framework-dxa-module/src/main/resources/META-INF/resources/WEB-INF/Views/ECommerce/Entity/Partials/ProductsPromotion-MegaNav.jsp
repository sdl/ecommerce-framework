<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="promotion" type="com.sdl.ecommerce.api.model.ProductsPromotion" scope="request"/>
<jsp:useBean id="viewHelper" type="com.sdl.ecommerce.dxa.ECommerceViewHelper" scope="request"/>
<jsp:useBean id="linkResolver" type="com.sdl.ecommerce.api.ECommerceLinkResolver" scope="request"/>
<div>
    <div class="row">
        <h5><i class="fa fa-cog"></i> ${promotion.title}
        <c:if test="${viewHelper.showEditControls(promotion)}">
         <a class="xpm-button popup-iframe" style="margin: 0px 8px;font-size: 14px;" href="${promotion.editUrl}"><i class="fa fa-pencil-square"></i></a>
        </c:if>
        </h5>

        <c:forEach var="product" items="${promotion.products}">
            <a href="${linkResolver.getProductDetailLink(product)}" class="product-teaser-link" style="display: flex;">
                <div class="col-sm-6">
                    <img src="${product.thumbnailUrl}" width="100%" height="100%" class="teaser-img loader-img" style="margin-bottom: 2px;"/>
                </div>
                <div class="col-sm-6 teaser-heading" style="padding-top: 4px; font-size: 12px; height:100px;">
                    <p style="max-height: 4em; overflow: hidden;">${product.name}</p>
                    <h4 class="teaser-heading">${product.price.formattedPrice}</h4>
                </div>
            </a>
        </c:forEach>
    </div>
</div>

