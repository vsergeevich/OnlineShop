/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Manufacturer;
import com.tyrin.exceptions.DBException;
import com.tyrin.swing.model.GetNewPanels;
import com.tyrin.swing.util.Config;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author Tyrin V. S.
 */
public class Menu extends JMenuBar {

    private final JMenuBar menu;

    public Menu() {
        menu = new JMenuBar();
        menu.setBorder(new BasicBorders.MenuBarBorder(Color.lightGray, Color.white));
        final JMenu view = new JMenu("Витрина");
        JMenuItem jmiCat = new JMenuItem("Категории");
        JMenuItem jmiProd = new JMenuItem("Все товары");
        JMenuItem jmiMan = new JMenuItem("Производители");
        JMenuItem jmiOrders = new JMenuItem("Заказы");
        view.add(jmiCat);
        view.add(jmiMan);
        view.add(jmiOrders);
        view.add(jmiProd);
        menu.add(view);

        final JMenu exitItem = new JMenu("Выход");
        menu.add(exitItem);

        exitItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите выйти?");
                if (rez == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        jmiProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                GetNewPanels.changePanel(new ProductsPanel().getPanel());
            }
        });

        jmiMan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                manPan();
            }
        });

        jmiCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                GetNewPanels.changePanel(new CategoryPanel().getPanel());
            }
        });
        
        jmiOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
//                GetNewPanels.changePanel(new OrdersPanel().getPanel());
            }
        });
    }

    public JMenuBar getMenu() {
        return this.menu;
    }

    private void manPan() {
        try {
            new SwingWorker<List<Manufacturer>, Void>() {
                @Override
                protected List<Manufacturer> doInBackground() throws Exception {
                    List<Manufacturer> lm = new ArrayList<>();
                    try {
                        lm = Config.getServiceFactory().getManufacturerService().getAllManufacturer();
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return lm;
                }

                @Override
                public void done() {
                    List<Manufacturer> list = null;
                    try {
                        list = get();
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    GetNewPanels.changePanel(new ManufacturersPanel(list).getPanel());
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
