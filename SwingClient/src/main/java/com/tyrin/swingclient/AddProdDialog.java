/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Category;
import com.tyrin.beans.Manufacturer;
import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IProductService;
import com.tyrin.swing.model.ImageFileCopy;
import com.tyrin.swing.util.Config;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Tyrin V. S. Класс, представляющий диалог добавления нового товара.
 */
public class AddProdDialog extends JFrame {

    private final IProductService prodComp;
    private final IManufacturerService manComp;
    private final ICategoryService catComp;
    private Map<String, Integer> categoryMapOfIndex;
    private Map<String, Integer> manufacturerMapOfIndex;
    private List<Category> catList;
    private List<Manufacturer> manList;
    private final JPanel main;
    private final JLabel name, cat, man, desc, price, image;
    private final JTextField nameText, descText, priceText;
    private final JComboBox<String> catBox, manBox;
    private final JButton addImage, saveButton;
    private final JPanel but, save;
    private JFileChooser jfc = null;
    private static File imageFile; //Файл изображения товара
    private final JDialog dialog;
    private final JCheckBox box; //Флаг наличия товара на складе

    public AddProdDialog() {
        prodComp = Config.getServiceFactory().getProductService();
        manComp = Config.getServiceFactory().getManufacturerService();
        catComp = Config.getServiceFactory().getCategoryService();
        buildMapCategoriesOfIds();
        buildMapManufacturerOfIds();
        main = new JPanel(new GridLayout(20, 1));
        but = new JPanel(new BorderLayout());
        save = new JPanel();
        name = new JLabel("<html><u><b><font size=4>Введите название товара</b></font></u></html>");
        cat = new JLabel("<html><u><b><font size=4>Выберите категорию</b></u></font></html>");
        man = new JLabel("<html><u><b><font size=4>Выберите производителя</b></font></u></html>");
        desc = new JLabel("<html><u><b><font size=4>Введите описание</font></b></u></html>");
        price = new JLabel("<html><u><b><font size=4>Введите цену</font></u></b></html>");
        box = new JCheckBox("<html><font size=4>Есть на складе</font></html>", true);
        image = new JLabel("<html><u><b><font size=4>Выберите изображение</font></b></u></html>");
        nameText = new JTextField("");
        descText = new JTextField("");
        priceText = new JTextField();
        addImage = new JButton("Обзор...");
        saveButton = new JButton("Сохранить...");
        addImage.setSize(20, 10);
        saveButton.setSize(30, 15);
        but.add(addImage, BorderLayout.WEST);
        save.add(saveButton, BorderLayout.WEST);
        catBox = addCatItems();
        manBox = addManItems();
        main.add(name);
        main.add(nameText);
        main.add(new JPanel());
        main.add(cat);
        main.add(catBox);
        main.add(new JPanel());
        main.add(man);
        main.add(manBox);
        main.add(new JPanel());
        main.add(desc);
        main.add(descText);
        main.add(new JPanel());
        main.add(price);
        main.add(priceText);
        main.add(new JPanel());
        main.add(box);
        main.add(new JPanel());
        main.add(image);
        main.add(but);
        main.add(save);

// Слушатель на кнопку "Сохранить"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        saveProd();
                    }
                });

            }
        });
//Слушатель на кнопку "Добавить изображение"
        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });
        dialog = new JDialog(this, "Добавление нового товара", true);
    }

    /**
     * Метод, который считывает введенные данные о новом товаре, проверяет их
     * корректность и сохраняет
     *
     */
    private void saveProd() {
        Product newProd = new Product();
        double prodprice;
        String newProdImage;

        newProd.setName(nameText.getText());
        newProd.setDesc(descText.getText());
        newProd.setCatId(categoryMapOfIndex.get((String) catBox.getSelectedItem()));
        newProd.setManId(manufacturerMapOfIndex.get((String) manBox.getSelectedItem()));
        if (box.isSelected()) {
            newProd.setAvailable((byte) 1);
        } else {
            newProd.setAvailable((byte) 0);
        }
        if (nameText.getText() == null && priceText.getText() == null) {
            JOptionPane.showMessageDialog(this, "Введите все параметры");
            return;
        }
        try {
            prodprice = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Укажите правильно цену");
            return;
        }
        newProd.setPrice(prodprice);
        if (imageFile == null) {
            newProdImage = null;
        } else {
            newProdImage = imageFile.getPath();
            newProdImage = ImageFileCopy.copyFileInTarget(newProdImage);
        }
        newProd.setImage(newProdImage);
        // Добавляем в базу
        doRequestToSaveProd(newProd, prodComp);
    }

    /**
     * Метод, который вызывает стандартый диалог выбора файла, и записывает
     * выбранный файл изображения
     */
    public void chooseFile() {
        jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        jfc.setFileFilter(filter);
        int res = jfc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
        }
        imageFile = jfc.getSelectedFile();
    }

    /**
     * Метод возвращающий экземпляр этого диалога
     *
     * @return JDialog
     */
    public JDialog createDialog() {
        dialog.setSize(500, 550);
        dialog.add(main);
        int X = Math.round((MainWindow.FRAME_WIDTH - 500) / 2);
        int Y = Math.round((MainWindow.FRAME_HEIGHT) / 8);
        dialog.setLocation(X, Y);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }

    /**
     * Метод, который создает JComboBox со списком категорий, для выбора
     * категории товара
     *
     * @return JComboBox
     */
    private JComboBox addCatItems() {
        JComboBox jcb = new JComboBox();
        for (Category c : catList) {
            jcb.addItem(c.getName());
        }
        return jcb;
    }

    /**
     * Метод, который создает JComboBox со списком производителей для выбора
     *
     * @return JComboBox
     */
    private JComboBox addManItems() {
        JComboBox jcb = new JComboBox();
        for (Manufacturer m : manList) {
            jcb.addItem(m.getName());
        }
        return jcb;
    }

    private void buildMapCategoriesOfIds() {
        catList = catComp.getAllCategory();
        categoryMapOfIndex = new HashMap<>();
        for (Category c : catList) {
            categoryMapOfIndex.put(c.getName(), c.getId());
        }
    }

    private void buildMapManufacturerOfIds() {
        manList = manComp.getAllManufacturer();
        manufacturerMapOfIndex = new HashMap<>();
        for (Manufacturer m : manList) {
            manufacturerMapOfIndex.put(m.getName(), m.getId());
        }
    }

    public void doRequestToSaveProd(final Product prod, final IProductService prodComp) {
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    dialog.setVisible(false);
                    try {
                        prodComp.addProduct(prod);
                        JOptionPane.showMessageDialog(null, "Saved");
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(AddProdDialog.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
