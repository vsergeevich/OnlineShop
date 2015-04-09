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
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Tyrin V. S. Класс, который описывает диалог изменения товара.
 */
public class UpdateDialog extends JFrame {

    private final IProductService prodComp;
    private final IManufacturerService manComp;
    private final ICategoryService catComp;
    private Map<String, Integer> categoryMapOfIndex;
    private Map<String, Integer> manufacturerMapOfIndex;
    private List<Category> catList;
    private List<Manufacturer> manList;
    private final JPanel main, save, but;
    private final JLabel name, cat, catCurr, manCurr, man;
    private final JLabel desc, image, price;
    private final JTextField nameText, descText, priceText;
    private final JComboBox<String> catBox, manBox;
    private final JButton addImage, saveButton;
    private JFileChooser jfc = null;
    private File imageFile; //Файл изображения
    private final JCheckBox box; //Флаг наличия на складе
    private final JDialog dialog;

    public UpdateDialog(final Map<String, String> map) {
        prodComp = Config.getServiceFactory().getProductService();
        manComp = Config.getServiceFactory().getManufacturerService();
        catComp = Config.getServiceFactory().getCategoryService();
        buildMapCategoriesOfIds();
        buildMapManufacturerOfIds();
        main = new JPanel(new GridLayout(23, 1));
        but = new JPanel(new BorderLayout());
        save = new JPanel(new BorderLayout());
        name = new JLabel("<html><u><font size=4>Введите название товара</font></u></html>");
        catCurr = new JLabel("<html><font font-weight=bold><b>Текущая категория:   </b></font><font><i>" + map.get("cat") + "</i></font></html>");
        cat = new JLabel("<html><u><font size=4>Выберите новую категорию</u></font></html>");
        manCurr = new JLabel("<html><b>Текущий производитель:   </b></font><font><i>" + map.get("man") + "</i></font></html>");
        man = new JLabel("<html><u><font size=4>Выберите нового производителя</font></u></html>");
        desc = new JLabel("<html><u><font size=4>Введите описание</font></u></html>");
        box = new JCheckBox("<html><font size=4>Есть на складе</font></html>", true);
        price = new JLabel("<html><u><font size=4>Введите цену</font></u></html>");
        image = new JLabel("<html><u><font size=4>Выберите изображение</font></u></html>");
        nameText = new JTextField(map.get("name"));
        descText = new JTextField(map.get("desc"));
        if (map.get("image") == null || map.get("image").equals("null")) {
            imageFile = null;
        } else {
            imageFile = new File(map.get("image"));
        }
        priceText = new JTextField(map.get("price"));
        addImage = new JButton("Обзор...");
        saveButton = new JButton("Сохранить...");
        addImage.setSize(20, 10);
        saveButton.setSize(30, 15);
        but.add(addImage, BorderLayout.WEST);
        save.add(saveButton, BorderLayout.EAST);
        catBox = addCatItems(map.get("cat"));
        manBox = addManItems(map.get("man"));
        main.add(name);
        main.add(nameText);
        main.add(new JPanel());
        main.add(catCurr);
        main.add(cat);
        main.add(catBox);
        main.add(new JPanel());
        main.add(manCurr);
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
        main.add(new JPanel());
        main.add(save);

//Слушатель кнопки "Сохранить"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateProd(Integer.parseInt(map.get("id")));
                    }
                });
                ProductsPanel.indSelectedProd = 0;
            }
        });

        //Слушатель кнопки "Добавить изображение"
        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });
        dialog = new JDialog(this, "Изменить товар", true);
    }

    /**
     * Метод, который считывает введенные занчения, проверяет их корректность и
     * сохраняет
     *
     * @param id
     */
    private void updateProd(int id) {
        Product newProduct = new Product();
        double prodprice;
        String newProdImage;
        // Вычитываем значения
        newProduct.setId(id);
        newProduct.setDesc(descText.getText());
        newProduct.setCatId(categoryMapOfIndex.get((String) catBox.getSelectedItem()));
        newProduct.setManId(manufacturerMapOfIndex.get((String) manBox.getSelectedItem()));

        if (box.isSelected()) {
            newProduct.setAvailable((byte) 1);
        } else {
            newProduct.setAvailable((byte) 0);
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
        newProduct.setName(nameText.getText());
        newProduct.setPrice(prodprice);
        if (imageFile == null) {
            newProdImage = null;
        } else {
            newProdImage = imageFile.getPath();
            newProdImage = ImageFileCopy.copyFileInTarget(newProdImage);
        }
        newProduct.setImage(newProdImage);
        // Добавляем
        doRequestToUpdateProd(newProduct, prodComp);
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
            imageFile = jfc.getSelectedFile();
        }
    }

    /**
     * Метод возвращающий экземпляр этого диалога
     *
     * @return JDialog
     */
    public JDialog createDialog() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialog.setSize(500, 600);
        dialog.add(main);
        int X = Math.round((MainWindow.FRAME_WIDTH - 500) / 2);
        int Y = Math.round((MainWindow.FRAME_HEIGHT) / 8);
        dialog.setLocation(X, Y);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }

    /*
     * Метод возвращант JComboBox списка категорий, и с выбранным по умолчанию пунктом, 
     *переданном в аргументе
     */
    private JComboBox addCatItems(String current) {
        JComboBox jcb = new JComboBox();
        for (Category c : catList) {
            jcb.addItem(c.getName());
        }
        jcb.setSelectedItem(current);
        return jcb;
    }

    /*
     * Метод возвращант JComboBox списка производителей, и с выбранным по умолчанию пунктом, 
     *переданном в аргументе
     */
    private JComboBox addManItems(String current) {
        JComboBox jcb = new JComboBox();
        for (Manufacturer m : manList) {
            jcb.addItem(m.getName());
        }
        jcb.setSelectedItem(current);
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

    public void doRequestToUpdateProd(final Product prod, final IProductService prodComp) {
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    dialog.setVisible(false);
                    try {
                        prodComp.updateProduct(prod);
                        JOptionPane.showMessageDialog(null, "Сохранено");
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }

            }.execute();
        } catch (Exception e) {
            Logger.getLogger(UpdateDialog.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
