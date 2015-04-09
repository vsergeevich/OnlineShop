/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.rest;

import com.tyrin.beans.Category;
import com.tyrin.services.ICategoryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tyrin V. S.
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryRestController {

    @Autowired
    @Qualifier("categorySpringComponent")
    private ICategoryService categoryComp;
    private static final Log log = LogFactory.getLog(CategoryRestController.class);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addCategory(@RequestBody Category cat) {
        log.info("POST-request on url */admin/category" + cat.getId());
        categoryComp.addCategory(cat);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Category getCategory(@PathVariable int id) {
        log.info("GET-request on url */admin/category" + id);
        return categoryComp.getCategory(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateCategory(@RequestBody Category cat) {
        log.info("PUT-request on url */admin/category" + cat.getId());
        categoryComp.updateCategory(cat);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody List<Category> getAllCategory() {
        log.info("GET-request on url */admin/category");
        return categoryComp.getAllCategory();
    }

    @RequestMapping(value = "/get-tree", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, String> getCategoryTree() {
        log.info("GET-request on url */admin/category/get-tree");
        return categoryComp.getCategoryTree();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        log.info("DELETE-request on url */admin/category/" + id);
        categoryComp.deleteCategory(id);
    }

}
