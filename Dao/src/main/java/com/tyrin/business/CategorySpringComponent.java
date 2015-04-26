/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.beans.Category;
import com.tyrin.services.ICategoryService;
import com.tyrin.dao.CategoryDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CategorySpringComponent implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;
    private static final Log log = LogFactory.getLog(CategorySpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCategory(Category cat) {
        categoryDao.addCategory(cat);
        log.info("Category " + cat.getName() + " is added to database");
    }

    @Override
    public Category getCategory(int catId) {
        log.info("Get category, id = " + catId);
        return categoryDao.getCategory(catId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCategory(Category cat) {
        categoryDao.updateCategory(cat);
        log.info("Category " + cat.getName() + " is updated");
    }

    @Override
    public List<Category> getAllCategory() {
        log.info("Get all category");
        return categoryDao.getAllCategory();
    }

    @Override
    public Map<String, String> getCategoryTree() {
        log.info("Get category tree");
        return categoryDao.getCategoryTree();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCategory(int catId) {
        categoryDao.deleteCategory(catId);
        log.info("Category " + catId + " is deleteded");
    }

    @Override
    public Map<Category, List<Category>> buildTreeForWeb(int parent) {
        return categoryDao.buildTreeForWeb(parent);
    }

    @Override
    public Map<Integer, String> mapIndexes() {
        Map<Integer, String> mapIndex = new HashMap<>();
        List<Category> list = categoryDao.getAllCategory();
        for (Category cat : list) {
            mapIndex.put(cat.getId(), cat.getName());
        }
        return mapIndex;
    }

}
