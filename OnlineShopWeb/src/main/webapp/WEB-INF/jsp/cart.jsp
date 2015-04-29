<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<table>
    <!-- here should go some titles... -->
    <tr>
        <th>ID</th>
        <th>NAME</th>
        
    </tr>
    <c:forEach items="${cartList}" var="product">
        
    <tr>
        <td>
            <c:out value="${product.id}" />
        </td>
        <td>
            <c:out value="${product.name}" />
        </td>
    </tr>
    </c:forEach>
</table>