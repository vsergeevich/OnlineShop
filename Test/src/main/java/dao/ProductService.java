/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.tyrin.beans.Product;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;

/**
 *
 * @author Tyrin V.S.
 */
@Service("productService")
@Transactional
public class ProductService {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<Product> getAll(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM  Product");
        return query.list();
    }

    public Product get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = (Product) session.get(Product.class, id);
        return product;
    }

    public void add(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
    }

    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = (Product) session.get(Product.class, id);
        session.delete(product);
    }

    public void edit(Product product) {
        Session session = sessionFactory.getCurrentSession();
        Product currentProduct = (Product) session.get(Product.class, product.getId());
        currentProduct.setCatId(product.getCatId());
        currentProduct.setManId(product.getManId());
        currentProduct.setName(product.getName());
        currentProduct.setAvailable(product.getAvailable());
        currentProduct.setImage(product.getImage());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setDesc(product.getDesc());
        session.save(currentProduct);
    }
}

