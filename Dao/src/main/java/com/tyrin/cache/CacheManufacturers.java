/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.cache;

import com.tyrin.beans.Manufacturer;
import com.tyrin.dao.ManufacturerDao;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyrin V.S.
 */
@Component
public class CacheManufacturers {

    private final ManufacturerDao manufacturerDao;
    private static Map<Integer, String> mapIndex;
    private static CopyOnWriteArrayList<Manufacturer> allManufacturers;
    private static final Log log = LogFactory.getLog(CacheManufacturers.class);

    @Autowired
    public CacheManufacturers(ManufacturerDao manufacturerDao) {
        this.manufacturerDao = manufacturerDao;
        mapIndex = null;
        allManufacturers = null;
    }

    public Map<Integer, String> mapIndexes() {
        if (mapIndex == null) {
            mapIndex = new ConcurrentHashMap<>();
            List<Manufacturer> list = manufacturerDao.getAllManufacturer();
            for (Manufacturer man : list) {
                mapIndex.put(man.getId(), man.getName());
            }
        }
        return mapIndex;
    }

    public List<Manufacturer> allManufacturers() {
        if (allManufacturers == null) {
            allManufacturers = new CopyOnWriteArrayList<>(manufacturerDao.getAllManufacturer());
            log.info("Manufacturers cache is built");
        }
        return allManufacturers;
    }

    public static void cleanCache() {
        mapIndex = null;
        allManufacturers = null;
        log.info("Manufacturers cache is cleaned");
    }
}
