/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.services;

import com.tyrin.beans.Order;
import java.util.List;

/**
 *
 * @author fits-dev
 */
public interface IOrderService {

    public void addOrder(Order order);
    
    public Order getOrder(int orderID);
    
    public void delOrder(int orderID);
    
    public List<Order> getAllOrders(); 
}
