/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.controllers;

import com.tyrin.beans.Order;
import com.tyrin.beans.Product;
import com.tyrin.services.IOrderService;
import com.tyrin.services.IProductService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tyrin VS
 */
@Controller
@Scope("session")
public class SessionController {

    private static final Log log = LogFactory.getLog(IndexController.class);
    private List<Integer> list = new LinkedList<>();
    @Autowired
    @Qualifier("productSpringComponent")
    IProductService prodComponent;
    @Autowired
    @Qualifier("orderSpringComponent")
    IOrderService orderComp;

    @RequestMapping(value = "/cart.htm", method = RequestMethod.POST)
    public void addToCart(HttpSession session, @RequestParam int id) {
        list.add(id);
        log.info("session add prod id = " + id);
        log.info("cart = " + list.toString());
        session.setAttribute("cart", "not empty");
    }

    @RequestMapping(value = "/showcart.htm", method = RequestMethod.GET)
    public ModelAndView showCart() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("cart");
        List<Product> listProds = new ArrayList<>();
        for (int i : list) {
            listProds.add(prodComponent.getProduct(i));
        }
        mav.addObject("cartList", listProds);
        return mav;
    }

    @RequestMapping(value = "/buy.htm", method = RequestMethod.GET)
    @ResponseBody
    public void buy(WebRequest webRequest) {
        log.info("in buy");
        Order order = new Order();
        order.setListProductsId(list);
        order.setFio(webRequest.getParameter("fio"));
        order.setMail(webRequest.getParameter("mail"));
        order.setPhone(webRequest.getParameter("phone"));
        order.setAdress(webRequest.getParameter("adress"));
        orderComp.addOrderItems(order);
        list.clear();
    }

}
