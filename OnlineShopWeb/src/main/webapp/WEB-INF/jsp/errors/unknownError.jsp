<%-- 
    Document   : dbError
    Created on : 04.04.2015, 16:40:04
    Author     : Tyrin V.S.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="text-info text-center">
    <h1>Произошла неизвестная ошибка</h1>
    <p>
        <code th:text="${ex}">Exception</code>
    </p>
</div>

