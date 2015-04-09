/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.beans.Order;
import com.tyrin.services.IOrderService;
import com.tyrin.dao.OrderDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author fits-dev
 */
@Component
public class OrderSpringComponent implements IOrderService {

    @Autowired
    private OrderDao orderDao;
    private static final Log log = LogFactory.getLog(OrderSpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void addOrder(Order order) {
        log.info("Order " + order.getOrderID() + " is added");
        orderDao.addOrder(order);
    }

    @Override
    public synchronized Order getOrder(int orderID) {
        return orderDao.getOrder(orderID);
    }

    @Override
    public synchronized List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public void delOrder(int orderID) {
        orderDao.delOrder(orderID);
        log.info("Order " + orderID + " is deleted");
    }

}
