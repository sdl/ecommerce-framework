<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<jsp:useBean id="entity" type="com.sdl.ecommerce.dxa.model.SearchBox" scope="request"/>
<jsp:useBean id="markup" type="com.sdl.webapp.common.markup.Markup" scope="request"/>
<jsp:useBean id="localization" type="com.sdl.webapp.common.api.localization.Localization" scope="request"/>
<div>
    <form class="navbar-form navbar-right navbar-search" action="${localization.localizePath('/search/_redirect')}" method="get">
        <div class="form-group search-box">
            <!-- TODO: Get placeholder text from the search box widget -->
            <i class="fa fa-search"/>
            <input name="q" type="text" class="form-control" autocomplete="off" placeholder="<dxa:resource key="e-commerce.searchPlaceholderText"/>" value="${eComSearchPhrase}">
        </div>
    </form>
</div>