/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.controllers;

import com.tyrin.beans.Category;
import com.tyrin.beans.Product;
import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IProductService;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tyrin V.S.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private final static int PAGE_SIZE = 12;
    private static final Log log = LogFactory.getLog(IndexController.class);
    @Autowired
    @Qualifier("productSpringComponent")
    IProductService prodComponent;
    @Autowired
    @Qualifier("categorySpringComponent")
    ICategoryService categoryComponent;
    @Autowired
    @Qualifier("manufacturerSpringComponent")
    IManufacturerService manufacturerComponent;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String allProducts(ModelMap model) {
        log.info("GET-request on url */index.htm");
        log.info("Loading main page");
        model.addAttribute("manMap", manufacturerComponent.mapIndexes());
        model.addAttribute("catMap", categoryComponent.mapIndexes());
        model.addAttribute("listMan", manufacturerComponent.getAllManufacturer());
        model.addAttribute("catTreeMap", categoryComponent.buildTreeForWeb(0));
        model.addAttribute("catLabel", new Category());
        model.addAttribute("list", prodComponent.getAllProduct(1, PAGE_SIZE));
        model.addAttribute("page", 1);
        model.addAttribute("moreGoods", "/WebShop/all-products/");
        String view = "index";
        return view;
    }

    @RequestMapping(value = "/all-products", method = RequestMethod.GET)
    public ModelAndView nextPageOfProducts(@RequestParam int page) {
        log.info("GET-request on url */all-products");
        log.info("Load goods on page " + page);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("showcase");
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        List<Product> list = prodComponent.getAllProduct(page + 1, PAGE_SIZE);
        mav.addObject("page", page);
        mav.addObject("moreGoods", "/WebShop/all-products/");
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping(value = "/search.htm", method = RequestMethod.POST, consumes = "application/json")
    public ModelAndView search(@RequestBody String request) {
        log.info("GET-request on url */search.htm");
        log.info("Search product on request " + request);
        ModelAndView mav = new ModelAndView();
        List<Product> list = prodComponent.searchProduct(request);
        mav.setViewName("showcase");
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        mav.addObject("list", list);
        return mav;
    }

    @RequestMapping(value = "/manufacturer/{id}", method = RequestMethod.GET)
    public ModelAndView filterManufacturers(@PathVariable int id, @RequestParam int page, @RequestParam Integer catId) {
        log.info("GET-request on url */manufacturer/" + id);
        log.info("Filtering by manufacturer id =" + id);
        ModelAndView mav = new ModelAndView();
        List<Product> list = prodComponent.getProductsByCategoryAndManufacturer(page, PAGE_SIZE, catId, id);
        mav.addObject("page", page);
        mav.setViewName("showcase");
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        mav.addObject("list", list);
        mav.addObject("moreGoods", "/WebShop/manufacturer/" + id);
        return mav;
    }

    @RequestMapping(value = "/price/{low}/{high}", method = RequestMethod.GET)
    public ModelAndView filterPrice(@PathVariable int low, @PathVariable int high, @RequestParam int page, @RequestParam Integer catId) {
        log.info("GET-request on url */price/" + low + "/" + high);
        log.info("Filtering by price range: " + low + " to " + high);
        ModelAndView mav = new ModelAndView();
        List<Product> list = prodComponent.getProductsByCategoryAndPrice(page, PAGE_SIZE, catId, new int[]{low, high});
        mav.setViewName("showcase");
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        mav.addObject("list", list);
        mav.addObject("page", page);
        mav.addObject("moreGoods", "/WebShop/price/" + low + "/" + high);
        return mav;
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public ModelAndView filterCat(@PathVariable int id, @RequestParam(required = false) Integer page) {
        log.info("GET-request on url */category/" + id);
        log.info("Loading page by category id = " + id);
        if (page == null) {
            page = 1;
        }
        ModelAndView mav = new ModelAndView("index");
        Category cat = id != 0 ? categoryComponent.getCategory(id) : new Category();
        Map<Category, List<Category>> map = categoryComponent.buildTreeForWeb(id);
        if (map.values().size() == 1) {
            cat.setName("");
        }
        List<Product> list = prodComponent.getProductsByCategory(page, PAGE_SIZE, id);
        mav.addObject("page", page);
        mav.addObject("list", list);
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        mav.addObject("listMan", manufacturerComponent.getAllManufacturer());
        mav.addObject("catTreeMap", map);
        mav.addObject("catLabel", cat);
        mav.addObject("moreGoods", "/WebShop/category-next/" + id);
        return mav;
    }

    @RequestMapping(value = "/category-next/{id}", method = RequestMethod.GET)
    public ModelAndView filterCatNext(@PathVariable int id, @RequestParam int page) {
        log.info("GET-request on url */category-next/" + id);
        log.info("Loading next page by category page = " + page);
        ModelAndView mav = new ModelAndView("showcase");
        List<Product> list = prodComponent.getProductsByCategory(page, PAGE_SIZE, id);
        mav.addObject("page", page);
        mav.addObject("manMap", manufacturerComponent.mapIndexes());
        mav.addObject("catMap", categoryComponent.mapIndexes());
        mav.addObject("list", list);
        mav.addObject("moreGoods", "/WebShop/category-next/" + id);
        return mav;
    }
}
