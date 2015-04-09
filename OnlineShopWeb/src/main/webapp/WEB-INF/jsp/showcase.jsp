<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id = "container">
    <c:set var="count" value="4" scope="page" />  
    <c:if test="${list.size() == 0}">
        <h4><p class="text-info text-center"><spring:message code="label.nogoods"/></p></h4>

    </c:if>
    <c:forEach items="${list}" var="product">
        <!-- выводим 1 продукт в строке -->
        <c:if test="${count%3==1}">
            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-3 text-center">
                    <ul class="columns">
                        <li>
                            <!-- выводим изображение продукта -->
                            <c:choose>
                                <c:when test="${product.image == ''}">
                                    <img src="<c:url value="/img/default.jpg"/>" alt="" width="200px" height="200px" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/${product.image}"/>" alt="" width="200px" height="200px" />
                                </c:otherwise>
                            </c:choose>
                            <!-- выводим название продукта -->
                            <h3>${product.name}</h3>
                            <div class="info">

                                <!-- выводим описание продукта -->
                                <p class="description">${product.desc}</p>
                                <dl>
                                    <dt class="title"><spring:message code="label.category"/></dt>
                                    <!-- выводим категорию продукта -->
                                    <dd class="value">${catMap.get(product.catId)}</dd>
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.manufacturer"/></dt>
                                    <!-- выводим производителя продукта -->
                                    <dd class="value">${manMap.get(product.manId)}</dd>
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.price"/></dt>
                                    <!-- выводим цену продукта -->
                                    <dd class="value">${product.price}</dd>
                                </dl>
                                <!-- флаг наличия продукта -->
                                <c:if test="${product.available==0}">
                                    <p><span class="label label-default"><spring:message code="label.availfalse"/></span></p>
                                    </c:if>
                                    <c:if test="${product.available==1}">
                                    <p><span class="label label-success"><spring:message code="label.availtrue"/></span></p>
                                    </c:if>

                                <button type="button" onclick="orderModal(this.id)" id="${product.id}" class="btn btn-sm btn-default"><spring:message code="label.buy"/></button>

                            </div>
                        </li>
                    </ul>
                </div>
            </c:if>

            <!-- выводим 2 продукт в строке-->        
            <c:if test="${count%3==2}">
                <div class="col-md-3 text-center">
                    <ul class="columns">
                        <li>
                            <!-- выводим изображение продукта -->
                            <c:choose>
                                <c:when test="${product.image == ''}">
                                    <img src="<c:url value="/img/default.jpg"/>" alt="" width="200px" height="200px" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/${product.image}"/>" alt="" width="200px" height="200px" />
                                </c:otherwise>
                            </c:choose>

                            <!-- выводим название продукта -->
                            <h3>${product.name}</h3>
                            <div class="info">

                                <!-- выводим описание продукта -->
                                <p class="description">${product.desc}</p>
                                <dl>
                                    <dt class="title"><spring:message code="label.category"/></dt>
                                    <!-- выводим категорию продукта -->
                                    <dd class="value">${catMap.get(product.catId)}</dd>
                                    <!--dd class="value">${product.catId}</dd-->
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.manufacturer"/></dt>
                                    <!-- выводим производителя продукта -->
                                    <dd class="value">${manMap.get(product.manId)}</dd>
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.price"/></dt>
                                    <!-- выводим цену продукта -->
                                    <dd class="value">${product.price}</dd>
                                </dl>
                                <!-- флаг наличия продукта -->
                                <c:if test="${product.available==0}">
                                    <p><span class="label label-default"><spring:message code="label.availfalse"/></span></p>
                                    </c:if>
                                    <c:if test="${product.available==1}">
                                    <p><span class="label label-success"><spring:message code="label.availtrue"/></span></p>
                                    </c:if>

                                <button type="button" onclick="orderModal(this.id)" id="${product.id}" class="btn btn-sm btn-default"><spring:message code="label.buy"/></button>

                            </div>
                        </li>
                    </ul>
                </div>
            </c:if>

            <!-- выводим 3 продукт в строке-->
            <c:if test="${count%3==0}">
                <div class="col-md-3 text-center">
                    <ul class="columns">
                        <li>
                            <!-- выводим изображение продукта -->
                            <c:choose>
                                <c:when test="${product.image == ''}">
                                    <img src="<c:url value="/img/default.jpg"/>" alt="" width="200px" height="200px" />
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/${product.image}"/>" alt="" width="200px" height="200px" />
                                </c:otherwise>
                            </c:choose>
                            <!-- выводим название продукта -->
                            <h3>${product.name}</h3>
                            <div class="info">

                                <!-- выводим описание продукта -->
                                <p class="description">${product.desc}</p>
                                <dl>
                                    <dt class="title"><spring:message code="label.category"/></dt>
                                    <!-- выводим категорию продукта -->
                                    <dd class="value">${catMap.get(product.catId)}</dd>
                                    <!--dd class="value">${product.catId}</dd-->
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.manufacturer"/></dt>
                                    <!-- выводим производителя продукта -->
                                    <dd class="value">${manMap.get(product.manId)}</dd>
                                </dl>
                                <dl>
                                    <dt class="title"><spring:message code="label.price"/></dt>
                                    <!-- выводим цену продукта -->
                                    <dd class="value">${product.price}</dd>
                                </dl>
                                <!-- флаг наличия продукта -->
                                <c:if test="${product.available==0}">
                                    <p><span class="label label-default"><spring:message code="label.availfalse"/></span></p>
                                    </c:if>
                                    <c:if test="${product.available==1}">
                                    <p><span class="label label-success"><spring:message code="label.availtrue"/></span></p>
                                    </c:if>
                                <button type="button" onclick="orderModal(this.id)" id="${product.id}" class="btn btn-sm btn-default"><spring:message code="label.buy"/></button>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>                
        </c:if>
        <c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>
</div>

<div id="moreprod" style="text-align: center;" itemprop="${moreGoods}">
    <c:if test="${list.size() == 12}">
        <a class="btn btn-primary" onclick="showMoreGoods(${page+1})" href="#" role="button"><spring:message code="label.moregoods"/></a>
    </c:if>
</div>
