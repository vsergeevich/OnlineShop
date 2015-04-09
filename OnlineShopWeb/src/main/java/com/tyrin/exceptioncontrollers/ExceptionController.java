/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.exceptioncontrollers;

import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Tyrin V.S.
 */
@ControllerAdvice(basePackages = "com.privatbank.controllers")
public class ExceptionController {

    private static final Log log = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public ModelAndView handleCustomException(Exception ex) {
        log.error(ex.getMessage());
        ModelAndView model = new ModelAndView("errors/dbError");
        model.addObject("ex", ex);
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {
        log.error(ex.getMessage());
        ModelAndView model = new ModelAndView("errors/unknownError");
        model.addObject("ex", ex);
        return model;
    }
}
