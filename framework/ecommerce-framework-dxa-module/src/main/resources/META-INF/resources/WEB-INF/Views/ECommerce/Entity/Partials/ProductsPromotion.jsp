<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="promotion" type="com.sdl.ecommerce.api.model.ProductsPromotion" scope="request"/>
<jsp:useBean id="viewHelper" type="com.sdl.ecommerce.dxa.ECommerceViewHelper" scope="request"/>
<jsp:useBean id="linkResolver" type="com.sdl.ecommerce.api.ECommerceLinkResolver" scope="request"/>
<div>

    <!-- TODO: Add info about the name of the promotion when in edit mode -->

    <div class="row">
        <h3 class="promotion-title"><i class="fa fa-arrow-circle-right"></i> ${promotion.title} <c:if test="${viewHelper.showEditControls(promotion)}"><a class="xpm-button popup-iframe" style="margin: 0px 8px;font-size: 14px;" href="${promotion.editUrl}"><i class="fa fa-pencil-square"></i></a></c:if></h3>
    </div>
    <div class="row">
        <c:forEach var="product" items="${promotion.products}">
            <div class="col-sm-6 col-md-3 product-promo">
                <a href="${linkResolver.getProductDetailLink(product)}" class="product-teaser-link">
                    <div class="teaser">
                        <img src="${product.thumbnailUrl}" class="teaser-img loader-img"/>
                        <div class="product-promo-ribbon">
                            <p class="teaser-description product-promo-name">
                                    ${product.name}
                            </p>
                            <h4 class="teaser-heading product-price">${product.price.formattedPrice}</h4>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>

