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
public interface IProductService {

    public void addProduct(Product prod);

    public Product getProduct(int prodID);

    public void updateProduct(Product prod);

    public void deleteProduct(int prodId);

    public List<Product> searchProduct(String mask);

    public List<Product> getAllProduct();

    public List<Product> getProductsByPrice(int low, int high);

    public List<Product> getProductByCategory(int categoryId);

    public List<Product> getProductByManufacturer(int manId);

    public List<Product> getProductByCategoryWithChildren(int catId);
}
