/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.cache.CacheProducts;
import com.tyrin.beans.Product;
import com.tyrin.services.IProductService;
import com.tyrin.dao.ProductDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyrin V. S.
 */
@Component
public class ProductSpringComponent implements IProductService {

    @Autowired
    private ProductDao productDao;
    private static final Log log = LogFactory.getLog(ProductSpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void addProduct(Product prod) {
        CacheProducts.cleanCache();
        productDao.addProduct(prod);
        log.info("Product " + prod.getName() + " is added");
    }

    @Override
    public synchronized Product getProduct(int prodID) {
        return productDao.getProduct(prodID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void updateProduct(Product prod) {
        CacheProducts.cleanCache();
        productDao.updateProduct(prod);
        log.info("Product " + prod.getName()+ " is updated");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void deleteProduct(int prodId) {
        CacheProducts.cleanCache();
        productDao.deleteProduct(prodId);
        log.info("Product " + prodId + " is deleted");
    }

    @Override
    public synchronized List<Product> getProductByCategory(int catId) {
        return productDao.getProductByCategory(catId);
    }

    @Override
    public synchronized List<Product> getProductByManufacturer(int manId) {
        return productDao.getProductByManufacturer(manId);
    }

    @Override
    public synchronized List<Product> searchProduct(String mask) {
        return productDao.searchProduct(mask);
    }

    @Override
    public synchronized List<Product> searchProductOnPrice(int low, int high) {
        List<Product> list = productDao.searchProductOnPrice(low, high);
        System.out.println(list.getClass());
        return list;
    }

    @Override
    public synchronized List<Product> getAllProduct() {
        List<Product> list = productDao.getAllProduct();
        System.out.println(list.getClass());
        return list;
    }

    public synchronized List<Product> getProductByCategoryWithChildren(int catId) {
        if (catId == 0) {
            return productDao.getAllProduct();
        } else {
            return productDao.getProductByCategoryWithChildren(catId);
        }
    }

}
