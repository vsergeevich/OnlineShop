/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Category;
import com.tyrin.exceptions.DBException;
import com.tyrin.swing.model.GetNewPanels;
import com.tyrin.services.ICategoryService;
import com.tyrin.swing.util.Config;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author Tyrin V. S. Класс, представляющий диалог добавления новой категории.
 * По умолчанию в JComboBox предлагается та родительская категория, которая
 * выбрана в дереве
 */
public class AddCatDialog extends JFrame {

    private final ICategoryService catComp;
    private final JPanel main, button;
    private final JLabel category, catParent;
    private final JTextField catText;
    private final JButton saveButton;
    private final JComboBox<String> catParentBox;
    public JDialog dialog;
    private Map<String, Integer> categoryMapOfIndex;
    List<Category> catList;

    public AddCatDialog(List<Category> catList) {
        this.catList = catList;
        catComp = Config.getServiceFactory().getCategoryService();
        main = new JPanel(new GridLayout(7, 1));
        button = new JPanel(new BorderLayout());
        category = new JLabel("Введите название категории");
        catText = new JTextField("");
        catParent = new JLabel("Выберите родительскую категорию");
        catParentBox = addCatItems();
        saveButton = new JButton("Сохранить...");
        button.add(saveButton, BorderLayout.EAST);
        main.add(category);
        main.add(catText);
        main.add(catParent);
        main.add(catParentBox);
        main.add(new JPanel());
        main.add(button);
//Слушатель на кнопку "Сохранить"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCategory();
            }
        });
        buildMapCategoriesOfIds();
        dialog = new JDialog(this, "Добавление новой категории", true);
    }

    /**
     * Метод, который сохраняет введенные данные в базу Выдает предупреждения,
     * если не все введено
     */
    private void saveCategory() {
        String newCatStr;
        String newParentCat;
        Category newCat;
        // Вычитываем значения
        newCatStr = catText.getText();
        if (newCatStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Введите все параметры");
            return;
        }
        newParentCat = (String) catParentBox.getSelectedItem();
        //У категории не может быть подкатегории с таким же названием, проверяем
        if (newCatStr.equals(newParentCat)) {
            JOptionPane.showMessageDialog(null, "Введите другое название категории");
            return;
        }

        //Если корень категорий - пишем в базу родителя 0
        if (newParentCat.equals("Без родителя")) {
            newCat = new Category(0, 0, newCatStr);
        } else {
            newCat = new Category(0, categoryMapOfIndex.get(newParentCat), newCatStr);
        }
        doRequestToAddCategory(newCat, catComp);
    }

    /**
     * Метод, который создает JComboBox со списком категорий, для выбора
     * родительской
     *
     * @return JComboBox
     */
    private JComboBox addCatItems() {
        JComboBox jcb = new JComboBox();
        jcb.addItem("Без родителя");
        for (Category c : catList) {
            jcb.addItem(c.getName());
        }
        if (CategoryPanel.selectedPath != null) {
            if (CategoryPanel.selectedPath.equals("Категории товаров")) {
                jcb.setSelectedItem("Без родителя");
            } else {
                jcb.setSelectedItem(CategoryPanel.selectedPath);
            }
        }
        return jcb;
    }

    private void buildMapCategoriesOfIds() {
        categoryMapOfIndex = new HashMap<>();
        for (Category c : catList) {
            categoryMapOfIndex.put(c.getName(), c.getId());
        }
    }

    /**
     * Метод возвращающий экземпляр этого диалога
     *
     * @return JDialog
     */
    public JDialog createDialog() {
        dialog.setSize(250, 300);
        dialog.add(main);
        int X = Math.round((MainWindow.FRAME_WIDTH - 250) / 2);
        int Y = Math.round((MainWindow.FRAME_HEIGHT) / 8);
        dialog.setLocation(X, Y);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }

    public void doRequestToAddCategory(final Category cat, final ICategoryService catComp) {
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    dialog.setVisible(false);
                    try {
                        catComp.addCategory(cat);
                        JOptionPane.showMessageDialog(null, "Сохранено");
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }

                @Override
                public void done() {
                    GetNewPanels.changePanel(new CategoryPanel().getPanel());
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(AddCatDialog.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
