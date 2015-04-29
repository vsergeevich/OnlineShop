<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="true" %>

<!DOCTYPE html>
<html>
    <head>
        <title><spring:message code="label.store"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />

        <!-- css include -->
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/css/product.css" />" rel="stylesheet">
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet">

        <!-- js include -->
        <script src="<c:url value="/js/jquery.js" />"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/js/jquery-ui.js" />"></script>
        <script src="<c:url value="/js/price.js" />"></script>
        <!--<script src="<c:url value="/js/sidebar-menu.js" />"></script>-->
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

                    <!--search from begin -->
                    <form class="navbar-form navbar-left" id="search" role="search" action="/WebShop/search.htm">
                        <div class="form-group">
                            <input id="searchStr"  maxlength="10" type="text" class="form-control" placeholder=<spring:message code="label.searchplace"/>>
                        </div>
                        <button type="submit" class="btn btn-default"><spring:message code="label.search"/></button>
                    </form>
                    <!--search from end -->
                    <form class="navbar-form navbar-right" id="gotoLogin" role="goToLogin" action="/WebShop/login.htm">
                        <button type="submit" class="btn btn-primary"><spring:message code="label.login"/></button>
                    </form>
                    <button type="button" onclick="orderModal()" class="btn btn-sm btn-default"><img src="img/cart.jpg" height="40">Корзина</button>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="?lang=en"> <img src="<c:url value="/img/uk.png" />" alt=""/></a></li>
                        <li><a href="?lang=ru"><img src="<c:url value="/img/rus.png" />" alt=""/></a></li>
                        <li><a href="?lang=ua"><img src="<c:url value="/img/ukraine.png" />" alt=""/></a></li>
                    </ul>                   
                </div>
            </div>
        </nav>