<%-- 
    Document   : admin
    Created on : 24.03.2015, 12:52:08
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin page</title>

        <!-- css include -->
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/css/login.css" />" rel="stylesheet">
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet">

        <!-- js include -->
        <script src="<c:url value="/js/jquery.js" />"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/js/price.js" />"></script>
    </head>
    <body>
        <!-- carousel begin -->
        <div id="gallery-carousel" class="carousel slide">
            <ol class="carousel-indicators">
                <li data-target="#gallery-carousel" data-slide-to="0" class="active"></li>
                <li data-target="#gallery-carousel" data-slide-to="1"></li>
                <li data-target="#gallery-carousel" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="item active" id="bevog-image-1">
                    <img src="<c:url value="/img/img_33.png" />" style="margin: 0 auto;"  alt="Bevog gallery picture title">
                </div>
                <div class="item" id="bevog-image-2">
                    <img src="<c:url value="/img/img_22.png" />" style="margin: 0 auto;" alt="Bevog gallery picture title">
                </div>
                <div class="item" id="bevog-image-3">
                    <img src="<c:url value="/img/img_11.png" />" style="margin: 0 auto;" alt="Bevog gallery picture title">
                </div>
            </div>
            <a class="left carousel-control" data-slide="prev" href="#gallery-carousel"><span class="icon-prev"></span></a>
            <a class="right carousel-control" data-slide="next" href="#gallery-carousel"><span class="icon-next"></span></a>
        </div> 
        <!-- carousel end -->

        <!-- top menu begin -->
        <nav class="navbar navbar-default  navbar-fixed" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/WebShop/index.htm"><spring:message code="label.store"/></a>
                </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">                    
                    <form class="navbar-form navbar-right" id="backToLogin" name="backToLogin" method="post" action="<c:url value="/j_spring_security_logout" />">
                        <button type="submit" class="btn btn-primary btn-sm">Выход</button>
                    </form>
                </div>
        </nav>  
        <div class="col-md-1 text-center">
            <table class="table danger">
                <thead>
                    <tr> 
                        <th><h4><span class="label label-danger"><spring:message code="label.category"/></span></h4></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listCat}" var="category">
                        <tr class="danger">
                            <td>${category.name}</td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>
        </div>
        <div class="col-md-8">      
            <table class="table success">
                <thead>
                    <tr> 
                        <th><h4><span class="label label-success">Код товара:</span></h4></th>
                        <th><h4><span class="label label-success">Наименование товара:</span></h4></th>
                        <th><h4><span class="label label-success">Цена:</span></h4></th>
                        <th><h4><span class="label label-success">Наличие:</span></h4></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listProd}" var="product">
                        <tr class="success">
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>${product.available}</td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>                 
            <table class="table info">
                <thead>
                    <tr> 
                        <th><h4><span class="label label-info">Код товара:</span></h4></th>
                        <th><h4><span class="label label-info"><spring:message code="label.name"/></span></h4></th>
                        <th><h4><span class="label label-info"><spring:message code="label.phone"/></span></h4></th>
                        <th><h4><span class="label label-info">Email:</span></h4></th>
                        <th><h4><span class="label label-info"><spring:message code="label.adress"/></span></h4></th>
                        <th><h4><span class="label label-info">Удаление</span></h4></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listOrder}" var="order">
                        <tr class="info">
                            <td>${order.productID}</td>
                            <td>${order.fio}</td>
                            <td>${order.phone}</td>
                            <td>${order.mail}</td>
                            <td>${order.adress}</td>
                            <td><a href="delete/${order.orderID}">Delete</a></td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>
        </div>
        <div class="col-md-3">
            <table class="table warning">
                <thead>
                    <tr> 
                        <th><h4><span class="label label-warning"><spring:message code="label.manufacturer"/></span></h4></th>
                        <th><h4><span class="label label-warning">Страна:</span></h4></th> 
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listMan}" var="manufacturer">
                        <tr class="warning">
                            <td>${manufacturer.name}</td>
                            <td>${manufacturer.country}</td>
                        </tr>
                    </c:forEach>  
                </tbody>
            </table>
        </div>
    </body>
</html> 