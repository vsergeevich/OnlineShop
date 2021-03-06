/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.services;

import com.tyrin.beans.Product;
import java.util.List;

/**
 *
 * @author Tyrin V. S.
 */
public interface IProductServicePageable extends IProductService {

    public List<Product> getProductsByPrice(int page, int pageSize, int low, int high);

    public List<Product> getAllProduct(int page, int pageSize);

    public List<Product> getProductsByCategory(int page, int pageSize, int categoryId);

    public List<Product> getProductsByManufacturer(int page, int pageSize, int manufacturerId);

    public List<Product> getProductsByCategoryAndManufacturer(int page, int pageSize, int categoryId, int manId);

    public List<Product> getProductsByCategoryAndPrice(int page, int pageSize, int categoryId, int[] priceRange);

}
