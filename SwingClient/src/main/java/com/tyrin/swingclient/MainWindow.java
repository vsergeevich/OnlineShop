/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.swing.model.GetNewPanels;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenuBar;
import javax.swing.*;

/**
 *
 * @author Tyrin V. S. Класс, представляющий главное окно программы. По
 * умолчанию на него добавлена панель категорий товаров
 */
public class MainWindow extends JFrame {

    public static int FRAME_WIDTH;
    public static int FRAME_HEIGHT;
    public static Container mainContainer;
    public static JMenuBar menu;

    public MainWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        FRAME_WIDTH = screenSize.width;
        FRAME_HEIGHT = (int) (rect.getBounds().getHeight() - getSize().getHeight());
        menu = new Menu().getMenu();
        mainContainer = getContentPane();
        setTitle("Интерфейс администрирования витрины интернет-магазина");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите выйти?");
                if (rez == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setVisible(true);
        mainContainer.add(menu, BorderLayout.NORTH);
        GetNewPanels.changePanel(new CategoryPanel().getPanel());
    }
}
