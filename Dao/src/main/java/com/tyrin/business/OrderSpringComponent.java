/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.beans.Order;
import com.tyrin.services.IOrderService;
import com.tyrin.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

/**
 *
 * @author user
 */
@Service
public class OrderSpringComponent implements IOrderService {

    @Autowired
    private OrderDao orderDao;
    private static final Log log = LogFactory.getLog(OrderSpringComponent.class);


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public void addOrderItems(Order order) {
        orderDao.addOrderItems(order);
    }
}
