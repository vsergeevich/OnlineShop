/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.model;

import com.tyrin.swingclient.MainWindow;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Tyrin V. S.
 * Класс, инкапсулирующий механизм замены панелей в главном окне
 */
public class GetNewPanels {
/**
 * Метод перерисовывает в главном окне панель, переданную в параметре
 * @param panel 
 */
    public static synchronized void changePanel(JPanel panel) {
        MainWindow.mainContainer.remove(0);
        panel.setVisible(true);
        MainWindow.mainContainer.add(panel, BorderLayout.CENTER, 0);
        MainWindow.mainContainer.revalidate();
        MainWindow.mainContainer.repaint();
    }
}
