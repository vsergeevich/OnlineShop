/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.cache.CacheCategories;
import com.tyrin.cache.CacheProducts;
import com.tyrin.beans.Category;
import com.tyrin.services.ICategoryService;
import com.tyrin.dao.CategoryDao;
import java.util.List;
import java.util.Map;
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
public class CategorySpringComponent implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;
    private static final Log log = LogFactory.getLog(CategorySpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void addCategory(Category cat) {
        CacheCategories.cleanCache();
        categoryDao.addCategory(cat);
        log.info("Category " + cat.getName()+ " is added to database");
    }

    @Override
    public synchronized Category getCategory(int catId) {
        log.info("Get category, id = " + catId);
        return categoryDao.getCategory(catId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void updateCategory(Category cat) {
        categoryDao.updateCategory(cat);
        log.info("Category " + cat.getName()+ " is updated");
        CacheProducts.cleanCache();
        CacheCategories.cleanCache();
    }

    @Override
    public synchronized List<Category> getAllCategory() {
        log.info("Get all category");
        return categoryDao.getAllCategory();
    }

    @Override
    public synchronized Map<String, String> getCategoryTree() {
        log.info("Get category tree");
        return categoryDao.getCategoryTree();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void deleteCategory(int catId) {
        CacheProducts.cleanCache();
        CacheCategories.cleanCache();
        categoryDao.deleteCategory(catId);
        log.info("Category " + catId + " is deleteded");
    }

    public Map<Category, List<Category>> buildTreeForWeb(int parent) {
        return categoryDao.buildTreeForWeb(parent);
    }
}
