/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.beans.Product;
import com.tyrin.services.IProductService;
import com.tyrin.dao.ProductDao;
import com.tyrin.services.IProductServicePageable;
import static java.lang.Math.min;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tyrin V. S.
 */
@Service
public class ProductSpringComponent implements IProductServicePageable {

    @Autowired
    private ProductDao productDao;
    private static final Log log = LogFactory.getLog(ProductSpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addProduct(Product prod) {
        productDao.addProduct(prod);
        log.info("Product " + prod.getName() + " is added");
    }

    @Override
    public Product getProduct(int prodID) {
        return productDao.getProduct(prodID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProduct(Product prod) {
        productDao.updateProduct(prod);
        log.info("Product " + prod.getName() + " is updated");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteProduct(int prodId) {
        productDao.deleteProduct(prodId);
        log.info("Product " + prodId + " is deleted");
    }

    @Override
    public List<Product> getProductByCategory(int catId) {
        return productDao.getProductByCategory(catId);
    }

    @Override
    public List<Product> getProductsByCategory(int page, int pageSize, int categoryId) {
        List<Product> listByCategory;
        if (categoryId == 0) {
            return productDao.getAllProduct(page, pageSize);
        } else {
            listByCategory = productDao.getProductByCategoryWithChildren(categoryId);
        }
        return listByCategory.subList((page - 1) * pageSize, min(page * pageSize, listByCategory.size()));
    }

    @Override
    public List<Product> getProductByManufacturer(int manId) {
        return productDao.getProductByManufacturer(manId);
    }

    @Override
    public List<Product> getProductsByManufacturer(int page, int pageSize, int manufacturerId) {
        return productDao.getProductByManufacturer(page, pageSize, manufacturerId);
    }

    @Override
    public List<Product> searchProduct(String mask) {
        return productDao.searchProduct(mask);
    }

    @Override
    public List<Product> getProductsByPrice(int low, int high) {
        List<Product> list = productDao.searchProductOnPrice(low, high);
        return list;
    }

    @Override
    public List<Product> getProductsByPrice(int page, int pageSize, int low, int high) {
        return productDao.searchProductOnPrice(page, pageSize, low, high);
    }

    @Override
    public List<Product> getProductsByCategoryAndManufacturer(int page, int pageSize, int categoryId, int manId) {
        if (categoryId == 0) {
            return getProductsByManufacturer(page, pageSize, manId);
        }
        List<Product> listByCategoryAndManufacturer = productDao.getProductByCategoryAndManufacturer(categoryId, manId);
        log.info("Return products by category " + categoryId + " and manufacturer " + manId);
        return listByCategoryAndManufacturer.subList((page - 1) * pageSize, min(page * pageSize, listByCategoryAndManufacturer.size()));
    }

    @Override
    public List<Product> getProductsByCategoryAndPrice(int page, int pageSize, int categoryId, int[] priceRange) {
        if (categoryId == 0) {
            return getProductsByPrice(page, pageSize, priceRange[0], priceRange[1]);
        }
        List<Product> listByCategoryAndPrice = productDao.getProductByCategoryAndPrice(categoryId, priceRange[0], priceRange[1]);
        log.info("Return products by category " + categoryId + " and price range " + Arrays.toString(priceRange));
        return listByCategoryAndPrice.subList((page - 1) * pageSize, min(page * pageSize, listByCategoryAndPrice.size()));
    }

    @Override
    public List<Product> getAllProduct(int page, int pageSize) {
        List<Product> list = productDao.getAllProduct(page, pageSize);
        return list;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> list = productDao.getAllProduct();
        return list;
    }

    @Override
    public List<Product> getProductByCategoryWithChildren(int catId) {
        if (catId == 0) {
            return productDao.getAllProduct();
        } else {
            return productDao.getProductByCategoryWithChildren(catId);
        }
    }

}
