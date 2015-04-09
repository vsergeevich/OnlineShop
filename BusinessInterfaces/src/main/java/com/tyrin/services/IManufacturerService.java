/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.services;

import com.tyrin.beans.Manufacturer;
import java.util.List;

/**
 *
 * @author Tyrin V. S.
 */
public interface IManufacturerService {

    public void addManufacturer(Manufacturer man);

    public void deleteManufacturer(int manId);

    public Manufacturer getManufacturer(int manId);

    public void updateManufactutrer(Manufacturer man);

    public List<Manufacturer> getAllManufacturer();

}
