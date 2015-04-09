package com.tyrin.swingclient;

import com.tyrin.swing.model.GetNewPanels;
import com.tyrin.swing.model.ProdTableModel;
import com.tyrin.beans.Manufacturer;
import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
import com.tyrin.swing.model.ManufacturerTableModel;
import com.tyrin.services.IManufacturerService;
import com.tyrin.services.IProductService;
import com.tyrin.swing.util.Config;
import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Tyrin V. S. Класс описывающий панель производителей. Динамически
 * добавляется таблица товаров по выбранному производителю и превью товара
 */
public class ManufacturersPanel extends JPanel {

    private final IProductService prodComp;
    private final IManufacturerService manComp;
    private List<Manufacturer> manList; //Список всех производителей
    public static JDialog addManDialog, addProdDialog, updateDialog, viewDialog;
    private final JPanel mainManPanel, manTablePanel, buttonPanel, small;
    private final JPanel menuPanel, viewPanel;
    private final JLabel manTabLabel;
    private JTable manListTable, prodListTable = new JTable();
    public JMenuBar menu;
    private String selectedFile;
    private static int indSelectedMan, indStrMan = 0; //Индекс строки и айди выбранного производителя
    private static int indSelectedProd, indStrProd = 0;//Индекс строки и айди выбранного товара

    public ManufacturersPanel(List<Manufacturer> manList) {
        this.manList = manList;
        prodComp = Config.getServiceFactory().getProductService();
        manComp = Config.getServiceFactory().getManufacturerService();
        small = new JPanel(new FlowLayout());
        manTablePanel = new JPanel(new BorderLayout());
        mainManPanel = new JPanel(new BorderLayout());
        Border b = new MotifBorders.BevelBorder(true, Color.darkGray, Color.lightGray);
        manTabLabel = new JLabel("Список производителей                                                Список товаров");
        manTabLabel.setBorder(b);
        manTabLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        manTabLabel.setHorizontalAlignment(JLabel.LEFT);
        manTablePanel.add(manTabLabel, BorderLayout.PAGE_START);

        //Кнопки
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addMan = new JButton("Добавить производителя");
        JButton delMan = new JButton("Удалить производителя");
        JButton addProd = new JButton("Добавить товар");
        JButton delProd = new JButton("Удалить товар");
        JButton updateProd = new JButton("Изменить товар");
        JButton viewProd = new JButton("Просмотр товара");

        JPanel m = new JPanel();
        m.setBorder(new TitledBorder(b, "<html><font color=blue>Управление производители"));
        m.add(addMan);
        m.add(delMan);
        buttonPanel.add(m);
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(b, "<html><font color=blue>Управление товарами"));
        p.add(addProd);
        p.add(delProd);
        p.add(viewProd);
        p.add(updateProd);
        buttonPanel.add(p);

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
        //Слушатель на кнопку "Изменить продукт"
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
        //Слушатель на кнопку "Добавить производиетля"
        addMan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addManDialog = new AddManDialog().createDialog();
            }
        });

        //Слушатель на кнопку "Удалить производителя"
        delMan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedManufacturer();
            }
        });

        //Слушатель на кнопку "Добавить Продукт"
        addProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewProduct();
            }
        });

//Слушатель на кнопку "Удалить продукт"
        delProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct();
            }
        });

        // Создаем таблицу производителей  
        manListTable = createManufacturersTable();

        //Слушатель для таблицы производителей, динамически добавляем список товаров
        ListSelectionModel lsm = manListTable.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                indStrMan = manListTable.getSelectedRow();
                if (indStrMan != -1) {
                    indSelectedMan = (int) manListTable.getValueAt(indStrMan, 0);
                }
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tableAdd(indSelectedMan);  //Добавляем таблицу товаров по выбранному производителю
                        viewPanel.removeAll();
                    }
                });
            }
        });

        JScrollPane jscrlp = new JScrollPane(manListTable);
        manListTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
        manTablePanel.add(jscrlp, BorderLayout.WEST);
        viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBorder(b);
        manTablePanel.add(small, BorderLayout.CENTER);
        menu = new Menu().getMenu();
        menuPanel = new JPanel(new BorderLayout());
        mainManPanel.add(manTablePanel, BorderLayout.CENTER);
        mainManPanel.add(buttonPanel, BorderLayout.NORTH);
        menuPanel.add(menu, BorderLayout.NORTH);
        menuPanel.add(mainManPanel, BorderLayout.CENTER);
        manTablePanel.add(viewPanel, BorderLayout.PAGE_END);
    }

    /**
     * Метод, возвращающий панель производителей
     *
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.menuPanel;
    }

    /**
     * Метод, который инамически меняет список товаров по производителю
     *
     * @param prodManId
     */
    private synchronized void tableAdd(final int prodManId) {
        indSelectedProd = 0;
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        List<Product> prodList;
                        prodList = prodComp.getProductByManufacturer(prodManId);
                        ProdTableModel prodTable = new ProdTableModel();
                        prodListTable = prodTable.getTable(prodList);

                        //Слушатель для таблицы товаров
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
                                    indStrProd = l.getMinSelectionIndex();
                                }
                                if (indStrProd != -1) {
                                    indSelectedProd = (int) prodListTable.getValueAt(indStrProd, 0);
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
                    prodListTable.setPreferredScrollableViewportSize(new Dimension(1000, 600)); //800800
                    small.removeAll();
                    small.add(jscrlp, BorderLayout.CENTER);
                    small.revalidate();
                    small.repaint();
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Метод, выполняющий удаление производителя. Выдает предупреждения.
     *
     */
    private void deleteSelectedManufacturer() {
        if (indSelectedMan == 0) {
            JOptionPane.showMessageDialog(null, "Выберите производителя в списке");
            return;
        }
        int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить выбранный производитель?");
        if (rez == JOptionPane.YES_OPTION) {
            try {
                new SwingWorker<List<Manufacturer>, Void>() {

                    @Override
                    protected List<Manufacturer> doInBackground() throws Exception {
                        List<Manufacturer> lm = new ArrayList<>();
                        try {
                            manComp.deleteManufacturer(indSelectedMan);
                            lm = manComp.getAllManufacturer();
                        } catch (DBException e) {
                            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                        }
                        return lm;
                    }

                    @Override
                    public void done() {
                        List<Manufacturer> list;
                        try {
                            list = get();
                            GetNewPanels.changePanel(new ManufacturersPanel(list).getPanel());
                        } catch (InterruptedException | ExecutionException ex) {
                            Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, ex);
                            throw new DBException(ex);
                        }
                    }
                }.execute();
            } catch (Exception e) {
                Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Метод, выполняющий удаление товара. Выдает предупреждения.
     *
     */
    private void deleteSelectedProduct() {
        if (indSelectedProd == 0) {
            JOptionPane.showMessageDialog(null, "Выберите товар в списке");
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
                        tableAdd(indSelectedMan);
                    }
                }.execute();
            } catch (Exception e) {
                Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Метод, котрый возвращает таблицу всех производителей из БД
     *
     * @return JTable
     */
    private JTable createManufacturersTable() {
        JTable table = new JTable();
//        try {
//            manList = manComp.getAllManufacturer();
//        } catch (DBException e) {
//            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка базы данных: " + e.getMessage());
//            return table; //Возвращаем пустую таблицу
//        }
        TableModel model = new ManufacturerTableModel(manList);
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    /**
     * Метод, который динамически меняет описание товара и его изображение в
     * нижней панели
     */
    private void previewProductOnSouthBorder() {
        Image image = null;
        try {
            selectedFile = (String) prodListTable.getValueAt(indStrProd, 7);
            if (selectedFile == null || selectedFile.equals("null")) {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } else {
                image = ImageIO.read(new File(selectedFile));
            }
        } catch (IOException ex) {
            try {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } catch (IOException ex1) {
                Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //Ужимание товара до нужного размера
        image = image.getScaledInstance(200, -100, Image.SCALE_SMOOTH);
        image = image.getScaledInstance(-100, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        JPanel prodText = new JPanel(new GridLayout(6, 1));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Название товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStrProd, 1) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Категория товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStrProd, 2) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Производитель товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStrProd, 3) + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Цена товара: </font><font size =+1>" + prodListTable.getValueAt(indStrProd, 4).toString() + "</font></html>"));
        prodText.add(new JLabel("<html><font color=blue size=+2>  Наличие: </font><font size =+1>" + (String) prodListTable.getValueAt(indStrProd, 6) + "</font></html>"));
        viewPanel.removeAll();
        viewPanel.add(prodText, BorderLayout.WEST);
        prodListTable.setPreferredScrollableViewportSize(new Dimension(1000, 390));
        viewPanel.add(new JPanel(), BorderLayout.CENTER);
        viewPanel.add(imageLabel, BorderLayout.EAST);
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
        map.put("id", prodListTable.getValueAt(indStrProd, 0).toString());
        map.put("name", (String) prodListTable.getValueAt(indStrProd, 1));
        map.put("cat", (String) prodListTable.getValueAt(indStrProd, 2));
        map.put("man", (String) prodListTable.getValueAt(indStrProd, 3));
        map.put("price", prodListTable.getValueAt(indStrProd, 4).toString());
        map.put("desc", (String) prodListTable.getValueAt(indStrProd, 5));
        map.put("avail", (String) prodListTable.getValueAt(indStrProd, 6));
        map.put("image", (String) prodListTable.getValueAt(indStrProd, 7));
        return map;
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
                    tableAdd(indSelectedMan);
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void addNewProduct() {
        try {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    addProdDialog = new AddProdDialog().createDialog();
                    return null;
                }

                @Override
                public void done() {
                    tableAdd(indSelectedMan);
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ManufacturersPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
