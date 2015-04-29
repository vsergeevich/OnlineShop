/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.rest;

import com.tyrin.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

//   

}
