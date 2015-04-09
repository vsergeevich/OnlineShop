/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IOrderService;
import com.tyrin.services.IProductService;

/**
 *
 * @author lenovo
 */
public interface IServiceFactory {

    public ICategoryService getCategoryService();

    public IManufacturerService getManufacturerService();

    public IProductService getProductService();
    
    public IOrderService getOrderService();
}
