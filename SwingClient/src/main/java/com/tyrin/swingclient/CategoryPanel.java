/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Category;
import com.tyrin.swing.model.GetNewPanels;
import com.tyrin.swing.model.CategoryTreeModel;
import com.tyrin.swing.model.ProdTableModel;
import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.ICategoryService;
import com.tyrin.services.IProductService;
import com.tyrin.swing.util.Config;
import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 *
 * @author Tyrin V. S. Панель с деревом категорий и списком товаров по выбранной
 * категории
 */
public class CategoryPanel extends JPanel {

    private final IProductService prodComp;
    private final ICategoryService catComp;
    private final JLabel catTableLabel;
    private Map<String, Integer> categoryMapOfIndex;
    private List<Category> catList;
    private JDialog addDialog, viewDialog, updateDialog;
    public JMenuBar menu;
    private JTree tree = null;
    private JScrollPane scroll;
    public static String selectedPath; //Выбранный узел дерева категорий
    public JPanel catListPanel, upperPanel, outer, middle, small, viewPanel;
    private static int indStr, indSelectedProd; //Индекс выбранной строки и айди товара в ней
    private JTable prodListTable;
    private String selectedFile; //Выбранный продукт в таблице

    public CategoryPanel() {
        prodComp = Config.getServiceFactory().getProductService();
        catComp = Config.getServiceFactory().getCategoryService();
        indSelectedProd = 0;
        selectedPath = null;
        upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addCat = new JButton("Добавить категорию");
        JButton delCat = new JButton("Удалить категорию");
        JButton addProd = new JButton("Добавить товар");
        JButton delProd = new JButton("Удалить товар");
        JButton viewProd = new JButton("Просмотр товара");
        JButton updateProd = new JButton("Изменить товар");

        //Слушатель на кнопку "Просмотр"
        viewProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indSelectedProd == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите товар для просмотра в списке");
                    return;
                }
                viewDialog = new ViewDialog(getSelectedProdView()).createDialog();
            }
        });
        //Слушатель на кнопку "Изменить"
        updateProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indSelectedProd == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите товар для изменения в списке");
                    return;
                }
                updateProduct();
            }
        });
        //Слушатель на кнопку "Добавить товар"
        addProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProd();
            }
        });
        //Слушатель на кнопку "Удалить товар"
        delProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProd();
            }
        });
        //Слушатель на кнопку "Удалить категорию"
        delCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedCategory();
            }
        });
        //Слушатель на кнопку "Добавить категорию"
        addCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog = new AddCatDialog(catList).createDialog();
            }
        });
//        Border b = new BasicBorders.FieldBorder(Color.magenta, Color.lightGray, Color.lightGray, Color.lightGray);
        Border b = new MotifBorders.BevelBorder(true, Color.darkGray, Color.lightGray);
        JPanel c = new JPanel();
        c.setBorder(new TitledBorder(b, "<html><font color=blue>Управление категориями"));
        c.add(addCat);
        c.add(delCat);
        upperPanel.add(c);
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(b, "<html><font color=blue>Управление товарами"));
        p.add(addProd);
        p.add(delProd);
        p.add(viewProd);
        p.add(updateProd);
        upperPanel.add(p);
        outer = new JPanel(new BorderLayout());
        catListPanel = new JPanel(new BorderLayout());
        middle = new JPanel(new BorderLayout());
        small = new JPanel(new FlowLayout());
        catTableLabel = new JLabel("Список категорий  товаров                                            Список товаров");
        catTableLabel.setBorder(b);
        catTableLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        catTableLabel.setHorizontalAlignment(JLabel.LEFT);
//        Border b = new BasicBorders.FieldBorder(Color.lightGray, Color.lightGray, Color.lightGray, Color.lightGray);
//        b = new TitledBorder(b, "jkdfhsblfb");
        middle.add(catTableLabel, BorderLayout.NORTH);
        catListPanel.add(upperPanel, BorderLayout.NORTH);
        catListPanel.add(middle, BorderLayout.CENTER);
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBorder(b);
        catListPanel.add(viewPanel, BorderLayout.PAGE_END);
//        Добавление дерева категорий
        addTree();
        prodListTable = new JTable();
        menu = new Menu().getMenu();
        outer.add(menu, BorderLayout.NORTH);
        outer.add(catListPanel, BorderLayout.CENTER);
        middle.add(small, BorderLayout.CENTER);
        buildMapCategoriesOfIds();
    }

    /**
     * Добавляет дерево категорий на панелль
     */
    private void addTree() {
        CategoryTreeModel ct = new CategoryTreeModel();
        tree = ct.getTree();
        scroll = new JScrollPane(tree);
        scroll.setMinimumSize(new Dimension(200, 200));
        scroll.setPreferredSize(new Dimension(300, 200));
        middle.add(scroll, BorderLayout.WEST);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath tp = e.getPath();
                selectedPath = tp.getLastPathComponent().toString();
                if (!selectedPath.equals("Категории товаров")) {
                    tableAdd(selectedPath);  //Добавляем таблицу товаров по выбранному Category
                }
            }
        });
    }

    /**
     * Метод, возвращающий панель категорий
     *
     * @return панель
     */
    public JPanel getPanel() {
        return this.outer;
    }

    /**
     * Меняет список товаров по выбранной категории
     *
     * @param category
     */
    private synchronized void tableAdd(final String category) {
        if (category == null) {
            return;
        }
        indSelectedProd = 0;
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    List<Product> prodList;
                    try {
                        prodList = prodComp.getProductByCategory(categoryMapOfIndex.get(category));
                        ProdTableModel prodTable = new ProdTableModel();
                        prodListTable = prodTable.getTable(prodList);
                        ListSelectionModel lsm = prodListTable.getSelectionModel();
                        lsm.addListSelectionListener(new ListSelectionListener() {
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                if (e.getValueIsAdjusting()) {
                                    return;
                                }
                                ListSelectionModel l = (ListSelectionModel) e.getSource();
                                // Номер текущей строки таблицы
                                if (!l.isSelectionEmpty()) {
                                    indStr = l.getMinSelectionIndex();
                                }
                                if (indStr != -1) {
                                    indSelectedProd = (int) prodListTable.getValueAt(indStr, 0);
                                    previewProductOnSouthBorder();
                                }
                            }
                        });

                        prodListTable.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                JTable table = (JTable) e.getSource();
                                Point p = e.getPoint();
                                int row = table.rowAtPoint(p);
                                if (e.getClickCount() == 2) {
                                    indSelectedProd = (int) prodListTable.getValueAt(row, 0);
                                    viewDialog = new ViewDialog(getSelectedProdView()).createDialog();
                                }
                            }
                        });
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }

                @Override
                public void done() {
                    JScrollPane jscrlp = new JScrollPane(prodListTable);
                    prodListTable.setPreferredScrollableViewportSize(new Dimension(1000, 800));
                    small.removeAll();
                    small.add(jscrlp, BorderLayout.CENTER);
                    small.revalidate();
                    small.repaint();
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(CategoryPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Метод, выполняющий удаление товара. Выдает предупреждения.
     *
     */
    private void deleteSelectedProd() {
        if (indSelectedProd == 0) {
            JOptionPane.showMessageDialog(null, "Выберите товар для удаления в списке");
            return;
        }
        int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить выбранный товар?");
        if (rez == JOptionPane.YES_OPTION) {
            try {
                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            prodComp.deleteProduct(indSelectedProd);
                        } catch (DBException e) {
                            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    public void done() {
                        tableAdd(selectedPath);
                    }
                }.execute();
            } catch (Exception e) {
                Logger.getLogger(CategoryPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void addProd() {
        try {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        addDialog = new AddProdDialog().createDialog();
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }

                @Override
                public void done() {
                    tableAdd(selectedPath);
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(CategoryPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void updateProduct() {
        try {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    updateDialog = new UpdateDialog(getSelectedProdView()).createDialog();
                    return null;
                }

                @Override
                public void done() {
                    tableAdd(selectedPath);
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(CategoryPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Метод, выполняющий удаление категории. Выдает предупреждения.
     *
     */
    private void deleteSelectedCategory() {
        if (selectedPath == null || selectedPath.equals("Категории товаров")) {

            JOptionPane.showMessageDialog(null, "Выберите категорию для удаления в списке");
            return;
        }
        int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить выбранную категорию и все подкатегории?");
        if (rez == JOptionPane.YES_OPTION) {
            try {
                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            catComp.deleteCategory(categoryMapOfIndex.get(selectedPath));
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
                Logger.getLogger(CategoryPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Строим мапу название категории-айди
     */
    private void buildMapCategoriesOfIds() {
        catList = catComp.getAllCategory();
        categoryMapOfIndex = new HashMap<>();
        for (Category cat : catList) {
            categoryMapOfIndex.put(cat.getName(), cat.getId());
        }
    }

    /**
     * Метод, который динамически меняет описание товара и его изображение в
     * нижней панели
     */
    private void previewProductOnSouthBorder() {
        Image image = null;
        try {
            selectedFile = (String) prodListTable.getValueAt(indStr, 7);
            if (selectedFile == null || selectedFile.equals("null")) {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } else {
                image = ImageIO.read(new File(selectedFile));
            }
        } catch (IOException ex) {
            try {
                image = ImageIO.read(new File("img\\noImage.jpg"));

            } catch (IOException ex1) {
                Logger.getLogger(CategoryPanel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Ужимание товара до нужного размера
        image = image.getScaledInstance(200, -100, Image.SCALE_SMOOTH);
        image = image.getScaledInstance(-100, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        JPanel prodText = new JPanel(new GridLayout(6, 1));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Название товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 1) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Категория товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 2) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Производитель товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 3) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Цена товара: </font><font size =+1>" + prodListTable.getValueAt(indStr, 4).toString() + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Наличие: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 6) + "</font></html>"));
        viewPanel.removeAll();
        viewPanel.add(prodText, BorderLayout.WEST);
        viewPanel.add(new JPanel(), BorderLayout.CENTER);
        viewPanel.add(imageLabel, BorderLayout.EAST);
        prodListTable.setPreferredScrollableViewportSize(new Dimension(1000, 390));
        viewPanel.revalidate();
        viewPanel.repaint();
    }

    /**
     * Метод, который вычитывает из выбранной строки данные, собирает в Мар для
     * передачи в диалог просмотра или изменения товара.
     *
     * @return
     */
    private Map<String, String> getSelectedProdView() {
        Map<String, String> map = new HashMap<>();
        map.put("id", prodListTable.getValueAt(indStr, 0).toString());
        map.put("name", (String) prodListTable.getValueAt(indStr, 1));
        map.put("cat", (String) prodListTable.getValueAt(indStr, 2));
        map.put("man", (String) prodListTable.getValueAt(indStr, 3));
        map.put("price", prodListTable.getValueAt(indStr, 4).toString());
        map.put("desc", (String) prodListTable.getValueAt(indStr, 5));
        map.put("avail", (String) prodListTable.getValueAt(indStr, 6));
        map.put("image", (String) prodListTable.getValueAt(indStr, 7));
        return map;
    }

}
