<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="product" type="com.sdl.ecommerce.api.model.Product" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="screenWidth" type="com.sdl.webapp.common.api.ScreenWidth" scope="request"/>

<div class="content" style="margin-bottom: 8px;">
    <div class="row">
        <div class="col-md-6">
           <c:if test="${not empty product.primaryImageUrl}">
               <img class="center-block teaser-img" width="100%" id="product-image" src="${product.primaryImageUrl}"/>
           </c:if>
        </div>
        <div class="col-md-6">
            <h3 class="product-title">
                ${product.name}
            </h3>
            <div class="product-description">
                <div>
                    <div>
                        ${product.description}
                    </div>
                    <h3 class="center-block" style="margin-top: 16px;">
                        ${product.price.formattedPrice}
                    </h3>
                </div>
                <div class="product-add-to-cart">
                    <a class="btn btn-primary add-to-cart-button" data-product-id="${product.id}"><dxa:resource key="e-commerce.addToCartLabel"/> <i
                            class="fa fa-cart-plus"></i></a>
                </div>
            </div>
        </div>
    </div>
</div>


