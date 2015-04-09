/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.start;

import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.tyrin.swingclient.MainWindow;
import com.tyrin.swing.util.Config;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author lenovo
 */
public class RestClientStart {

    public static void main(String[] args) {
        String siteUrl = "http://localhost:8105/WebShop";
        String login = "admin";
        String password = "admin";
        Config.setFactory(new RestServiceFactory(siteUrl, login, password));
        try (FileInputStream in = new FileInputStream("swing.properties")) {
            Properties prop = new Properties();
            prop.load(in);
            SmartLookAndFeel.setCurrentTheme(prop);
            UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(RestClientStart.class.getName()).log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow();
            }
        });
    }
}
