/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.util;

import com.tyrin.swingclient.IServiceFactory;

/**
 *
 * @author Tyrin
 */
public class Config {

    private static IServiceFactory serviceFactory = null;

    public static void setFactory(IServiceFactory iServiceFactory) {
        serviceFactory = iServiceFactory;
    }

    public static IServiceFactory getServiceFactory() {
        return serviceFactory;
    }

}
