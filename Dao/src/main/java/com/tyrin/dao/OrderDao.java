/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.dao;

import com.tyrin.beans.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author fits-dev
 */
@Component
public class OrderDao {

    @Autowired
    private JdbcTemplate template;
    private static final Log log = LogFactory.getLog(OrderDao.class);

    class OrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int i) throws SQLException {
            Order order = new Order();
            order.setOrderID(rs.getInt("order_id"));
            order.setProductID(rs.getInt("order_prodID"));
            order.setFio(rs.getString("fio"));
            order.setMail(rs.getString("mail"));
            order.setPhone(rs.getString("phone"));
            order.setAdress(rs.getString("address"));
            return order;
        }
    }
    
    public Order getOrder(int orderID) {
        return template.queryForObject("SELECT * FROM Orders "
                                     + "WHERE order_id = ?", new OrderMapper(), orderID);
    }
    
    public List<Order> getAllOrders() {
        return template.query("SELECT * FROM Orders", new OrderMapper());
    }
    
    public void addOrder(Order order) {
        template.update("AddOrder ?,?,?,?,?", order.getProductID(), order.getFio(), order.getMail(), 
                                              order.getPhone(), order.getAdress());
    }
    
    public void delOrder(int orderID) {
        template.update("DELETE FROM Orders "
                      + "WHERE order_id = ?", orderID);
    }

}
