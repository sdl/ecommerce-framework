<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dxa" uri="http://www.sdl.com/tridion-dxa" %>
<%@ taglib prefix="xpm" uri="http://www.sdl.com/tridion-xpm" %>
<%--
<jsp:useBean id="editMenu" type="com.sdl.ecommerce.api.edit.EditMenu" scope="request"/>
--%>
<c:if test="${editMenu != null && editMenu.menuItems.size() gt 0}">
    <div>

        <!-- TODO: Should this also include message bar where modifications etc can be highlighted????  -->

        <div class="xpm-button xpm-create-new-menu">

            <!-- TODO: Use standard Tridion fonts here -->
            <!-- TODO: Visualize that there is a modification active for current page -->

            <div class="btn-group bootstrap-select" style="width: 140px;">
                <button type="button" class="btn dropdown-toggle selectpicker btn-default" data-toggle="dropdown"
                        title="${editMenu.title}">
                    <span class="filter-option pull-left"><i class="fa fa-plus"></i> ${editMenu.title}</span>&nbsp;<span class="caret"></span></button>
                <div class="dropdown-menu open" style="min-width: 0px; max-height: 197px; overflow: hidden; min-height: 0px;">
                    <ul class="dropdown-menu inner selectpicker" role="menu"
                        style="max-height: 185px; overflow-y: auto; min-height: 0px;">
                        <c:forEach var="menuItem" items="${editMenu.menuItems}" varStatus="status">
                            <li rel="${status.index}"><a tabindex="0" class="popup-iframe" href="${menuItem.url}"><span class="text">${menuItem.title}</span><i class="glyphicon glyphicon-ok icon-ok check-mark"></i></a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <dxa:definePluggableMarkup label="bottom-js">
            <script type="text/javascript">
                $(document).ready(function() {
                    $("#page-header .page-border").css("position", "relative");
                    $(".xpm-create-new-menu").detach().appendTo("#page-header .page-border");
                });
            </script>
        </dxa:definePluggableMarkup>
    </div>
</c:if>