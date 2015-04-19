/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.business;

import com.tyrin.beans.Manufacturer;
import com.tyrin.services.IManufacturerService;
import com.tyrin.dao.ManufacturerDao;
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
public class ManufacturerSpringComponent implements IManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;
    private static final Log log = LogFactory.getLog(ManufacturerSpringComponent.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void addManufacturer(Manufacturer man) {
        manufacturerDao.addManufacturer(man);
        log.info("Manufacturer " + man.getName()+ " is added to db");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized void deleteManufacturer(int manId) {
        manufacturerDao.deleteManufacturer(manId);
        log.info("Manufacturer " + manId + " is deleted from db");
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
    }

    @Override
    public synchronized List<Manufacturer> getAllManufacturer() {
        return manufacturerDao.getAllManufacturer();
    }
    
    @Override
    public Map<Integer, String> mapIndexes() {
            Map<Integer, String> mapIndex = new HashMap<>();
            List<Manufacturer> list = manufacturerDao.getAllManufacturer();
            for (Manufacturer man : list) {
                mapIndex.put(man.getId(), man.getName());
        }
        return mapIndex;
    }
}
