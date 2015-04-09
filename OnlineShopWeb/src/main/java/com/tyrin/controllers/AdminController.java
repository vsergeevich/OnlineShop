package com.tyrin.controllers;

/**
 *
 * @author user
 */
import com.tyrin.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    private static final Log log = LogFactory.getLog(AdminController.class);

    @Autowired
    @Qualifier("orderSpringComponent")
    IOrderService orderComp;

    @RequestMapping("/delete/{orderID}")
    public String delProducts(@PathVariable int orderID) {
        log.info("Deleting order with id =" + orderID);
        orderComp.delOrder(orderID);
        return "redirect:/admin";
    }
}
