/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.services;

import com.tyrin.beans.Category;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tyrin V. S.
 */
public interface ICategoryService {

    public void addCategory(Category cat);

    public void deleteCategory(int catId);

    public Category getCategory(int catId);

    public void updateCategory(Category cat);

    public List<Category> getAllCategory();

    public Map<String, String> getCategoryTree();

}
