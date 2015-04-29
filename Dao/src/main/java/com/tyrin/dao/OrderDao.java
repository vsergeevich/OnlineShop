/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.dao;

import com.tyrin.beans.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fits-dev
 */
@Repository
public class OrderDao {
    
    @Autowired
    private JdbcTemplate template;
    private static final Log log = LogFactory.getLog(OrderDao.class);
    
    public void addOrderItems(Order order) {
        template.update("INSERT INTO Purchase(fio, mail, phone, address) "
                + "VALUES (?, ?, ?, ?)", order.getFio(), order.getMail(), order.getPhone(), order.getAdress());
        int generatedValue = template.queryForInt("select last_insert_id()");
        for (int id : order.getListProductsId()) {
            template.update("INSERT INTO OrderItems(order_id, prod_id) "
                    + "VALUES (?, ?)", generatedValue, id);
        }
//    public List<OrderItem> getOrederItems() {
//        return template.query("SELECT * FROM Orders", new RowMapper<OrderItem>() {
//
//            @Override
//            public OrderItem mapRow(ResultSet rs, int i) throws SQLException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//            
//        });
    }
}
