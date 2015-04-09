/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.cache;

import com.tyrin.beans.Category;
import com.tyrin.dao.CategoryDao;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyirin V.S.
 */
@Component
public class CacheCategories {

    private final CategoryDao categoryDao;
    private static Map<Integer, String> mapIndex;
    private static Map<Integer, Map<Category, List<Category>>> mapCategoryTreeOfIndex;
    private static Map<Integer, Category> mapCategoryByIndex;
    private static final Log log = LogFactory.getLog(CacheCategories.class);

    @Autowired
    public CacheCategories(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        mapIndex = null;
        mapCategoryTreeOfIndex = null;
        mapCategoryByIndex = null;
    }

    public Map<Integer, String> mapIndexes() {
        if (mapIndex == null) {
            mapIndex = new ConcurrentHashMap<>();
            List<Category> list = categoryDao.getAllCategory();
            for (Category cat : list) {
                mapIndex.put(cat.getId(), cat.getName());
            }
        }
        return mapIndex;
    }

    public Category getCategory(int id) {
        Category category;
        if (mapCategoryByIndex == null) {
            mapCategoryByIndex = new ConcurrentHashMap<>();
            category = categoryDao.getCategory(id);
            mapCategoryByIndex.put(id, category);
        } else if (!mapCategoryByIndex.containsKey(id)) {
            category = categoryDao.getCategory(id);
            mapCategoryByIndex.put(id, category);
        } else {
            category = mapCategoryByIndex.get(id);
        }
        return category;
    }

    public Map<Category, List<Category>> getTreeCategories(int id) {
        Map<Category, List<Category>> map;
        if (mapCategoryTreeOfIndex == null) {
            mapCategoryTreeOfIndex = new ConcurrentHashMap<>();
            log.info("Builded cache tree of categories");
            map = categoryDao.buildTreeForWeb(id);
            mapCategoryTreeOfIndex.put(id, map);
        } else if (!mapCategoryTreeOfIndex.containsKey(id)) {
            map = categoryDao.buildTreeForWeb(id);
            mapCategoryTreeOfIndex.put(id, map);
        } else {
            map = mapCategoryTreeOfIndex.get(id);
        }
        return map;
    }

    public static void cleanCache() {
        mapIndex = null;
        mapCategoryTreeOfIndex = null;
        mapCategoryByIndex = null;
        log.info("Category cache is cleaned");
    }
}
