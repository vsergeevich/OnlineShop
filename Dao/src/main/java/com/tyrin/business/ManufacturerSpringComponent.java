/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.cache.CacheManufacturers;
import com.tyrin.cache.CacheProducts;
import com.tyrin.beans.Manufacturer;
import com.tyrin.services.IManufacturerService;
import com.tyrin.dao.ManufacturerDao;
import java.util.List;
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
public class ManufacturerSpringComponent implements IManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;
    private static final Log log = LogFactory.getLog(ManufacturerSpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void addManufacturer(Manufacturer man) {
        manufacturerDao.addManufacturer(man);
        log.info("Manufacturer " + man.getName()+ " is added to db");
        CacheManufacturers.cleanCache();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void deleteManufacturer(int manId) {
        manufacturerDao.deleteManufacturer(manId);
        log.info("Manufacturer " + manId + " is deleted from db");
        CacheManufacturers.cleanCache();
        CacheProducts.cleanCache();
    }

    @Override
    public synchronized Manufacturer getManufacturer(int manId) {
        return manufacturerDao.getManufacturer(manId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void updateManufactutrer(Manufacturer man) {
        manufacturerDao.updateManufactutrer(man);
        log.info("Manufacturer " + man.getName()+ " is updated");
        CacheManufacturers.cleanCache();
        CacheProducts.cleanCache();
    }

    @Override
    public synchronized List<Manufacturer> getAllManufacturer() {
        return manufacturerDao.getAllManufacturer();
    }
}
