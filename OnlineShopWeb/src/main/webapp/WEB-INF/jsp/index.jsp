<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file="/WEB-INF/jsp/header.jsp" %>
<!--sidebar begin-->
<div class="row-fluid" style="position: absolute"> 
    <div class="col-md-2 text-center">
        <c:if test="${catLabel.id!=0}">
            <a href="/WebShop/category/${catLabel.parentId}/?page=1"><img src="<c:url value="/img/back-arrow.jpg"/>"/></a><br>
            <script>
                document.getElementById("category").style.visibility = "hidden";
            </script>
            <p class="text-info">${catLabel.name}</p>
        </c:if>
        <div  id = "currentCategory" itemprop="${catLabel.id}"></div>
        <h3><span id="category" class="label label-primary"><spring:message code="label.categories"/></span></h3>
        <ul class="main-menu">
            <c:forEach items="${catTreeMap}" var="entry">
                <li><a href="/WebShop/category/${entry.key.id}/?page=1">${entry.key.name}</a>
                    <c:forEach items="${entry.value}" var="category" varStatus="loop">
                        <ul class="sub-menu">
                            <li><a href="/WebShop/category/${category.id}/?page=1">${category.name}</a></li>
                        </ul>
                    </c:forEach>
                </li>
            </c:forEach>
        </ul>
        <form method="post" id="manPanel" action="/WebShop/manufacturer">
            <div class="checkbox-container" id="manDiv">
                <h3><span class="label label-primary"><spring:message code="label.manufacturers"/></span></h3>
                    <c:forEach items="${listMan}" var="manufacturer">
                    <div class="checkbox">
                        <label><input type="radio" name="checkbox" value="${manufacturer.id}">${manufacturer.name}</label>
                    </div>
                </c:forEach>
            </div>
            <button type="submit" class="btn btn-sm btn-default" style="margin-left: 50px;"><spring:message code="label.filter"/></button>
        </form>
        <script src="<c:url value="/js/listener.js" />"></script>
        <div class="checkbox-container" id="priceDiv">
            <h3><span class="label label-primary"><spring:message code="label.pricefilter"/></span></h3>
            <p>
                <input type="text" id="amount" readonly style="border:0; color:#f6931f; font-weight:bold;">
            </p>
            <div id="slider-range"></div>
        </div>
    </div>
</div>
<!--sidebar end-->
<c:set var="count" value="4" scope="page" />    
<div id="testdiv">
    <%@ include file="/WEB-INF/jsp/showcase.jsp" %>
</div>

<div id="order">
    <%@ include file="/WEB-INF/jsp/orderProducts.jsp" %>
</div>

</body>
</html>


