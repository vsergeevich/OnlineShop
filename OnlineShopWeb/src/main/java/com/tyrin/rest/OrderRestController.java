/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.rest;

import com.tyrin.beans.Order;
import com.tyrin.services.IOrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/admin/order")
public class OrderRestController {

    @Autowired
    @Qualifier("orderSpringComponent")
    private IOrderService orderComp;
    private static final Log log = LogFactory.getLog(OrderRestController.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable int id) {
        log.info("DELETE-request on url */admin/order/" + id);
        orderComp.delOrder(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Order> getAllOrders() {
        log.info("GET-request on url */admin/order/");
        return orderComp.getAllOrders();
    }

}
