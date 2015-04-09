/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.tyrin.beans.Product;
import dao.ProductService;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author lenovo
 */
public class start {
   
    
    public static void main(String[] args) {
//        GenericXmlApplicationContext cont = new GenericXmlApplicationContext();
        
       ApplicationContext cont = new FileSystemXmlApplicationContext("applicationContext.xml");
//        cont..load("applicationContext.xml");
        ProductService productService = cont.getBean("productService", ProductService.class);
        SessionFactory sessionFactory = cont.getBean("sessionFactory", SessionFactory.class);
        List<Product> list = productService.getAll(sessionFactory);
        System.out.println(list);
    }
}
