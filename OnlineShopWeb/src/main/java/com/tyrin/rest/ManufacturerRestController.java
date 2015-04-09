/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.rest;

import com.tyrin.beans.Manufacturer;
import com.tyrin.services.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tyrin V. S.
 */
@RestController
@RequestMapping("/admin/manufacturer")
public class ManufacturerRestController {

    private static final Log log = LogFactory.getLog(ManufacturerRestController.class);

    @Autowired
    @Qualifier("manufacturerSpringComponent")
    private IManufacturerService manComponent;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Manufacturer getManufacturer(@PathVariable int id) {
        log.info("GET-request on url */admin/manufacturer/" + id);
        return manComponent.getManufacturer(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteManufacturer(@PathVariable int id) {
        log.info("DELETE-request on url */admin/manufacturer/" + id);
        manComponent.deleteManufacturer(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateManufactutrer(@RequestBody Manufacturer man) {
        log.info("PUT-request on url */admin/manufacturer/" + man.getId());
        manComponent.updateManufactutrer(man);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addManufacturer(@RequestBody Manufacturer man) {
        log.info("POST-request on url */admin/manufacturer/" + man.getId());
        manComponent.addManufacturer(man);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Manufacturer> getAllManufacturer() {
        log.info("GET-request on url */admin/manufacturer");
        return manComponent.getAllManufacturer();
    }

}
