/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.controllers;

import com.tyrin.beans.Order;
import com.tyrin.business.OrderSpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author user
 */
@Controller
@RequestMapping("/order.htm")
public class OrderController {

    private static final Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    OrderSpringComponent orderComp;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void order(WebRequest webRequest) {
        log.info("Order product");
        Order order = new Order();
        order.setProductID(Integer.parseInt(webRequest.getParameter("productID")));
        order.setFio(webRequest.getParameter("fio"));
        order.setMail(webRequest.getParameter("mail"));
        order.setPhone(webRequest.getParameter("phone"));
        order.setAdress(webRequest.getParameter("adress"));
        orderComp.addOrder(order);
        log.info("Order product id = " + webRequest.getParameter("productID") + " comleted");
    }
}
