<%-- 
    Document   : login
    Created on : 24.03.2015, 12:51:30
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login page</title>

        <!-- css include -->
        <link href="<c:url value="/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/css/login.css" />" rel="stylesheet">

        <!-- js include -->
        <script src="<c:url value="/js/jquery.js" />"></script>
        <script src="<c:url value="/js/bootstrap.min.js" />"></script>
        <script src="<c:url value="/js/jquery-ui.js" />"></script>
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
                    <img src="<c:url value="/img/a.jpg" />" style="margin: 0 auto;"  alt="Bevog gallery picture title">
                </div>
                <div class="item" id="bevog-image-2">
                    <img src="<c:url value="/img/b.jpg" />" style="margin: 0 auto;" alt="Bevog gallery picture title">
                </div>
                <div class="item" id="bevog-image-3">
                    <img src="<c:url value="/img/c.jpg" />" style="margin: 0 auto;" alt="Bevog gallery picture title">
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
        </nav>
        <div class="main">
            <div class="center-block">  
                <form id="login_form" name="login_form" method="post" action="<c:url value='j_spring_security_check' />">  

                    <table class="table">  
                        <tr class="active">  
                            <td colspan="2"><p class="text-danger">${message}</p></td>  
                        </tr>  
                    </table> 

                    <div class="form-group has-warning">
                        <h2><span class="label label-warning">Login:</span></h2>
                        <!-- <label class="control-label" for="exampleInputEmail1">Login:</label> -->
                        <input class="form-control input-lg" id="exampleInputEmail1" placeholder="Enter login" maxlength="15" size="15" type="text" name="username" />  
                    </div>
                    <div class="form-group has-warning">
                        <h2><span class="label label-warning">Password:</span></h2>
                        <!-- <label class="control-label" for="exampleInputPassword1">Password:</label> -->
                        <input class="form-control input-lg" id="exampleInputPassword1" placeholder="Password" name="password" size="15" type="password" /> 
                    </div>
                    <button type="submit" class="btn btn-warning btn-lg btn-center">LogIn!</button>
                    <button type="reset" class="btn btn-warning btn-lg btn-center">Reset</button>
                </form>
            </div> 
        </div>
    </body>
</html> 