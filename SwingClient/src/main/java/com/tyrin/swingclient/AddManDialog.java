/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Manufacturer;
import com.tyrin.exceptions.DBException;
import com.tyrin.swing.model.GetNewPanels;
import com.tyrin.services.IManufacturerService;
import com.tyrin.swing.util.Config;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author Tyrin V. S. Класс, представляющий диалог добавления нового
 * производителя
 */
public class AddManDialog extends JFrame {

    private final IManufacturerService manComp;
    private final JPanel main;
    private final JPanel button;
    private final JLabel country, name;
    private final JTextField nameText, countryText;
    private final JButton saveButton;
    private final JDialog dialog;

    public AddManDialog() {
        manComp = Config.getServiceFactory().getManufacturerService();
        main = new JPanel(new GridLayout(6, 1));
        button = new JPanel(new BorderLayout());
        name = new JLabel("Введите название производителя");
        country = new JLabel("Выберите страну производителя");
        nameText = new JTextField("");
        countryText = new JTextField("");
        saveButton = new JButton("Сохранить...");
        button.add(saveButton, BorderLayout.EAST);
        main.add(name);
        main.add(nameText);
        main.add(country);
        main.add(countryText);
        main.add(new JPanel());
        main.add(button);

// Слушатель кнопки "Сохранит"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        saveMan();
                    }
                });

            }
        });
        dialog = new JDialog(this, "Добавление нового производителя", true);
    }

    /**
     * Метод, который сохраняет введенные данные в базу Выдает предупреждения,
     * если не все введено
     */
    private void saveMan() {
        String newManName;
        String newManCountry;
        // Вычитываем значения
        newManName = nameText.getText();
        newManCountry = countryText.getText();
        if (newManName.isEmpty()) {
            JOptionPane.showMessageDialog(ManufacturersPanel.addManDialog, "Введите все параметры");
            return;
        }
        // Добавляем
        Manufacturer man = new Manufacturer(0, newManName, newManCountry);
        doRequestToAddManufacturer(man, manComp);
    }

    /**
     * Метод возвращающий экземпляр этого диалога
     *
     * @return JDialog
     */
    public JDialog createDialog() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialog.setSize(300, 200);
        dialog.add(main);
        int X = Math.round((MainWindow.FRAME_WIDTH - 300) / 2);
        int Y = Math.round((MainWindow.FRAME_HEIGHT) / 8);
        dialog.setLocation(X, Y);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }

    private void doRequestToAddManufacturer(final Manufacturer man, final IManufacturerService ms) {
        try {
            new SwingWorker<List<Manufacturer>, Void>() {

                @Override
                protected List<Manufacturer> doInBackground() throws Exception {
                    dialog.setVisible(false);
                    try {
                        ms.addManufacturer(man);
                        JOptionPane.showMessageDialog(null, "Сохранено");
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return ms.getAllManufacturer();
                }

                @Override
                public void done() {
                    List<Manufacturer> list;
                    try {
                        list = get();
                        GetNewPanels.changePanel(new ManufacturersPanel(list).getPanel());
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(AddManDialog.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + ex.getMessage());
                    }
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(AddManDialog.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
