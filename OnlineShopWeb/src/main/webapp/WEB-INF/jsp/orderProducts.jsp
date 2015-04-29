<%-- 
    Document   : orderProducts
    Created on : 22.03.2015, 17:37:30
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false"%>
<%@ page session="true" %>
<!-- Modal window-->
<form role="form" id="orderProduct" list7p="" >
    <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"><spring:message code="label.order"/></h4>
                        <div id ="cartItems">
                            </div>
                </div>
                <div class="modal-body">
                    <div class="form-group" style="display: block">
                        <label for="usr"><spring:message code="label.name"/></label>
                        <input type="text"  class="form-control" id="fio" required="">
                    </div>
                    <div class="form-group" style="display: block">
                        <label for="pwd">Email:</label>
                        <input type="text"  class="form-control" id="mail" required="">
                    </div>
                    <div class="form-group" style="display: block">
                        <label for="pwd"><spring:message code="label.phone"/></label>
                        <input type="text"  class="form-control" id="phone" required="">
                    </div>
                    <div class="form-group" style="display: block">
                        <label for="pwd"><spring:message code="label.adress"/></label>
                        <input type="text"  class="form-control" id="adress" required="">
                    </div> 
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.cancel"/></button>
                    <button  type="submit" class="btn btn-success "><spring:message code="label.add"/></button>
                </div>

            </div>

        </div>
    </div>
</form>
