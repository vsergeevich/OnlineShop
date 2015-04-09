package com.tyrin.controllers;

/**
 *
 * @author user
 */
import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IOrderService;
import com.tyrin.services.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;

@Controller
public class LoginController {

    private static final Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    @Qualifier("categorySpringComponent")
    ICategoryService catComp;
    @Autowired
    @Qualifier("manufacturerSpringComponent")
    IManufacturerService manComp;
    @Autowired
    @Qualifier("productSpringComponent")
    IProductService prodComp;
    @Autowired
    @Qualifier("orderSpringComponent")
    IOrderService orderComp;

    @RequestMapping("login")
    public ModelAndView getLoginForm(
            @RequestParam(required = false) String authfailed, String logout,
            String denied) {
        log.info("Loaded login page");
        String message = "Enter your login and password";
        if (authfailed != null) {
            message = "Invalid username or password, try again !";
        } else if (logout != null) {
            message = "Logged Out successfully, login again to continue !";
        } else if (denied != null) {
            message = "Access denied for this user !";
        }
        return new ModelAndView("login", "message", message);
    }

    @RequestMapping("admin")
    public String geAdminPage(ModelMap model) {
        log.info("Loaded components for page");
        model.addAttribute("listMan", manComp.getAllManufacturer());
        model.addAttribute("listCat", catComp.getAllCategory());
        model.addAttribute("listProd", prodComp.getAllProduct());
        model.addAttribute("listOrder", orderComp.getAllOrders());
        log.info("Loaded admin page");
        return "admin";
    }

    @RequestMapping("403page")
    public String ge403denied() {
        return "redirect:login?denied";
    }
}
