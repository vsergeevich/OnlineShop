/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.start;

import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IOrderService;
import com.tyrin.services.IProductService;
import com.tyrin.swingclient.IServiceFactory;
import com.tyrin.restclient.CategoryRestClient;
import com.tyrin.restclient.ManufacturerRestClient;
import com.tyrin.restclient.OrderRestClient;
import com.tyrin.restclient.ProductRestClient;

/**
 *
 * @author Tyrin
 */
public class RestServiceFactory implements IServiceFactory{
    private final CategoryRestClient categoryService;
    private final ManufacturerRestClient manufacturerService;
    private final ProductRestClient productService;
    private final OrderRestClient orderService;

    public RestServiceFactory(String siteUrl, String login, String password) {
        categoryService = new CategoryRestClient(siteUrl, login, password);
        manufacturerService = new ManufacturerRestClient(siteUrl, login, password);
        productService = new ProductRestClient(siteUrl, login, password);
        orderService = new OrderRestClient(siteUrl, login, password);
    }

    @Override
    public ICategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    public IManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    @Override
    public IProductService getProductService() {
        return productService;
    }

    @Override
    public IOrderService getOrderService() {
        return orderService;
    }
    
    
    
    
}
