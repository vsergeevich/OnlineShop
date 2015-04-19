/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.rest;

import com.tyrin.beans.Product;
import com.tyrin.services.IProductService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyrin V. S.
 */
@RestController
@RequestMapping("/admin/product")
public class ProductRestController {

    @Autowired
    @Qualifier("productSpringComponent")
    private IProductService prodComponent;
    private static final Log log = LogFactory.getLog(ProductRestController.class);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addProduct(@RequestBody Product prod) {
        log.info("POST-request on url */admin/product");
        prodComponent.addProduct(prod);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable int id, @RequestBody Product prod) {
        log.info("PUT-request on url */admin/product/" + id);
        prodComponent.updateProduct(prod);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Product getProduct(@PathVariable int id) {
        log.info("GET-request on url */admin/product/" + id);
        return prodComponent.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        log.info("DELETE-request on url */admin/product/" + id);
        prodComponent.deleteProduct(id);
    }

    @RequestMapping(value = "/by-category/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Product> getProductByCategory(@PathVariable int id) {
        log.info("GET-request on url */admin/product/by-category/" + id);
        return prodComponent.getProductByCategory(id);
    }

    @RequestMapping(value = "/by-manufacturer/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Product> getProductByManufacturer(@PathVariable int id) {
        log.info("GET-request on url */admin/product/by-manufacturer/" + id);
        return prodComponent.getProductByManufacturer(id);
    }

    @RequestMapping(value = "search/{mask}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Product> searchProduct(@PathVariable String mask) {
        log.info("GET-request on url */admin/product/search");
        try {
            return prodComponent.searchProduct(URLDecoder.decode(mask, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    @RequestMapping(value = "on-price/{low}/{high}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Product> searchProductOnPrice(@PathVariable int low, @PathVariable int high) {
        log.info("GET-request on url */admin/product/on-price/" + low + "/" + high);
        return prodComponent.getProductsByPrice(low, high);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Product> getAllProduct() {
        log.info("Get-request on url */admin/product");
        return prodComponent.getAllProduct();
    }

    
}
