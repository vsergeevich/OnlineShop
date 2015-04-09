/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.cache;

import com.tyrin.beans.Product;
import com.tyrin.dao.ProductDao;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyrin
 */
@Component
public class CacheProducts {

    private final ProductDao productDao;
    private static CopyOnWriteArrayList<Product> allProduct;
    private static Map<Integer, List<Product>> filterMapByManufacturer;
    private static Map<Integer, List<Product>> filterMapByCategory;
    private static Map<Integer[], List<Product>> filterMapByPrice;
    private static final Log log = LogFactory.getLog(CacheProducts.class);

    @Autowired
    public CacheProducts(ProductDao productDao) {
        this.productDao = productDao;
        allProduct = new CopyOnWriteArrayList<>(productDao.getAllProduct());
        filterMapByManufacturer = null;
        filterMapByCategory = null;
        filterMapByPrice = null;
    }

    public List<Product> getCachedProducts(int page, int pageSize) {
        List<Product> list;
        if (allProduct == null) {
            allProduct = new CopyOnWriteArrayList<>(productDao.getAllProduct());
            log.info("Cache of all porducts is built");
        }
        list = allProduct.subList((page - 1) * pageSize, min(page * pageSize, allProduct.size()));
        return list;
    }

    public List<Product> getCachedProductsByManufacturer(int page, int pageSize, int manufacturerId) {
        List<Product> pagebleList;
        List<Product> listByManufacturer;
        if (filterMapByManufacturer == null) {
            filterMapByManufacturer = new ConcurrentHashMap<>();
            listByManufacturer = productDao.getProductByManufacturer(manufacturerId);
            filterMapByManufacturer.put(manufacturerId, listByManufacturer);
            log.info("Cache of porducts by manufacturer " + manufacturerId + " is built");
        } else if ((!filterMapByManufacturer.containsKey(manufacturerId))) {
            listByManufacturer = productDao.getProductByManufacturer(manufacturerId);
            filterMapByManufacturer.put(manufacturerId, listByManufacturer);
            log.info("Cache of porducts by manufacturer " + manufacturerId + " is built");
        } else {
            listByManufacturer = filterMapByManufacturer.get(manufacturerId);
        }
        pagebleList = listByManufacturer.subList((page - 1) * pageSize, min(page * pageSize, listByManufacturer.size()));
        return pagebleList;
    }

    public List<Product> getCachedProductsByCategory(int page, int pageSize, int categoryId) {
        List<Product> pagebleList;
        List<Product> listByCategory;
        if (filterMapByCategory == null) {
            filterMapByCategory = new ConcurrentHashMap<>();
            if (categoryId == 0) {
                listByCategory = allProduct;
            } else {
                listByCategory = productDao.getProductByCategoryWithChildren(categoryId);
            }
            filterMapByCategory.put(categoryId, listByCategory);
            log.info("Cache of porducts by category " + categoryId + " is built");
        } else if ((!filterMapByCategory.containsKey(categoryId))) {
            if (categoryId == 0) {
                listByCategory = allProduct;
            } else {
                listByCategory = productDao.getProductByCategoryWithChildren(categoryId);
            }
            filterMapByCategory.put(categoryId, listByCategory);
        } else {
            listByCategory = filterMapByCategory.get(categoryId);
            log.info("Cache of porducts by category " + categoryId + " is built");
        }
        if (pageSize < 0) {
            return listByCategory;
        }
        pagebleList = listByCategory.subList((page - 1) * pageSize, min(page * pageSize, listByCategory.size()));
        return pagebleList;
    }

    public List<Product> getProductsByCategoryAndManufacturer(int page, int pageSize, int categoryId, int manId) {
        if (categoryId == 0) {
            return getCachedProductsByManufacturer(page, pageSize, manId);
        }
        List<Product> listByCategory = getCachedProductsByCategory(0, -1, categoryId);
        List<Product> listByManufacturer = new ArrayList<>();
        Iterator iter = listByCategory.listIterator();
        Product p;
        while (iter.hasNext()) {
            p = (Product) iter.next();
            if (p.getManId() == manId) {
                listByManufacturer.add(p);
            }
        }
        log.info("Return products by category " + categoryId + " and manufacturer " + manId);
        return listByManufacturer.subList((page - 1) * pageSize, min(page * pageSize, listByManufacturer.size()));
    }

    public List<Product> getProductsByCategoryAndPrice(int page, int pageSize, int categoryId, int[] priceRange) {
        if (categoryId == 0) {
            return getCachedProductsByPrice(page, pageSize, priceRange[0], priceRange[1]);
        }
        List<Product> listByCategory = getCachedProductsByCategory(0, -1, categoryId);
        List<Product> listByPrice = new ArrayList<>();
        Iterator iter = listByCategory.listIterator();
        double price;
        Product prod;
        while (iter.hasNext()) {
            prod = (Product) iter.next();
            price = prod.getPrice();
            if (price > priceRange[0] & price < priceRange[1]) {
                listByPrice.add(prod);
            }
        }
        log.info("Return products by category " + categoryId + " and price range " + Arrays.toString(priceRange));
        return listByPrice.subList((page - 1) * pageSize, min(page * pageSize, listByPrice.size()));
    }

    public List<Product> getCachedProductsByPrice(int page, int pageSize, int low, int high) {
        log.info("Cache products by price range " + low + "-" + high);
        List<Product> pagebleList;
        List<Product> listByPrice;
        Integer[] params = new Integer[]{low, high};
        if (filterMapByPrice == null) {
            filterMapByPrice = new ConcurrentHashMap<>();
            listByPrice = productDao.searchProductOnPrice(low, high);
            filterMapByPrice.put(params, listByPrice);
        } else if ((!filterMapByPrice.containsKey(params))) {
            listByPrice = productDao.searchProductOnPrice(low, high);
            filterMapByPrice.put(params, listByPrice);
        } else {
            listByPrice = filterMapByPrice.get(params);
        }
        if ((listByPrice.size() <= pageSize)) {
            return listByPrice;
        } else {
            pagebleList = listByPrice.subList((page - 1) * pageSize, min(page * pageSize, listByPrice.size()));
            return pagebleList;
        }
    }

    public static void cleanCache() {
        setAllProduct(null);
        setFilterMapByManufacturer(null);
        setFilterMapByCategory(null);
        setFilterMapByPrice(null);
        log.info("Cache of products is cleaned");
    }

    private static void setAllProduct(List<Product> allProduct) {
        CacheProducts.allProduct = (CopyOnWriteArrayList<Product>) allProduct;
    }

    private static void setFilterMapByManufacturer(Map<Integer, List<Product>> filterMapByManufacturer) {
        CacheProducts.filterMapByManufacturer = filterMapByManufacturer;
    }

    private static void setFilterMapByCategory(Map<Integer, List<Product>> filterMapByCategory) {
        CacheProducts.filterMapByCategory = filterMapByCategory;
    }

    private static void setFilterMapByPrice(Map<Integer[], List<Product>> filterMapByPrice) {
        CacheProducts.filterMapByPrice = filterMapByPrice;
    }

}
