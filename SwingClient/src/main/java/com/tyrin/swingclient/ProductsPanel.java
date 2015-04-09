package com.tyrin.swingclient;

import com.tyrin.swing.model.ProdTableModel;
import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Tyrin V. S.
 */
public class ProductsPanel extends JPanel {

    private IProductService prodComp;
    private JPanel prodTablePanel, menuPanel, upperPanel, back;
    private final JPanel main, viewPanel;
    private JLabel prodTableLabel;
    private JTable prodListTable;
    public static JDialog addProdDialog, updateProdDialog, viewDialog;
    private final JMenuBar menu;
    private JTextField searchText;
    private static String searchStr; //Введенная строка для поиска
    public static int indStr, indSelectedProd;//Индекс строки и айди выбранного товара
    boolean flagsearch;//true - при нажатии кнопки поиск, false - "К списку всех товаров"
    private JPanel searchPanel;
    private String selectedFile; //Путь к изображению выбранного продукта в таблице
    public String label;
    JPanel prodText;
    private List<Product> prodList;

    public ProductsPanel() {
        prodComp = Config.getServiceFactory().getProductService();
        prodList = new ArrayList<>();
        indSelectedProd = 0;
        flagsearch = false;
        prodTablePanel = new JPanel(new BorderLayout());
        menuPanel = new JPanel(new BorderLayout());

        //Кнопки
        prodTableLabel = new JLabel();
        upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bView = new JButton("Просмотр товара");
        JButton bAdd = new JButton("Добавить товар");
        JButton bUpdate = new JButton("Изменить товар");
        JButton bDelete = new JButton("Удалить товар");
        final JButton search = new JButton("Поиск");
        search.setPreferredSize(new Dimension(70, 20));
        searchPanel = new JPanel(new FlowLayout());
        searchText = new JTextField(30);
        searchPanel.add(searchText);
        Border border = BorderFactory.createEmptyBorder(0, 350, 0, 0);
        searchPanel.setBorder(border);
        searchPanel.add(search);
        Border b = new MotifBorders.BevelBorder(true, Color.darkGray, Color.lightGray);
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(b, "<html><font color=blue>Управление товарами"));
        p.add(bView);
        p.add(bAdd);
        p.add(bUpdate);
        p.add(bDelete);
        upperPanel.add(p);
        upperPanel.add(searchPanel);
//Слушатель кнопки "Добавить товар"
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewProduct();
            }
        });

//Слушатель кнопки "Изменить товар"
        bUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ProductsPanel.indSelectedProd == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите товар для изменения в списке");
                    return;
                }
                updateProduct();
            }
        });

//Слушатель кнопки "Просмотр товара"
        bView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ProductsPanel.indSelectedProd == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите товар для просмотра в списке");
                    return;
                }
                viewDialog = new ViewDialog(getSelectedProdView()).createDialog();
            }
        });

//Слушатель кнопки "Удалить товар"
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct();
            }
        });

//Слушатель кнопки "Поиск" 
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPanel.removeAll();
                search();
            }
        });
//Слушатель кнопки "Поиск" с клавиатуры
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    viewPanel.removeAll();
                    search();
                }
            }
        });

//При вводе текста в поле поиска устанавливается кнопка по умолчанию "Энтер"
        searchText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.getRootPane(searchPanel).setDefaultButton(search);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        main = new JPanel(new BorderLayout());
        main.add(prodTablePanel, BorderLayout.CENTER);
        main.add(upperPanel, BorderLayout.NORTH);
        viewPanel = new JPanel(new BorderLayout());
        menu = new Menu().getMenu();
        menuPanel.add(menu, BorderLayout.NORTH);
        menuPanel.add(main, BorderLayout.CENTER);
        main.add(viewPanel, BorderLayout.PAGE_END);
        getContentPanel();

    }

    /**
     * Метод, возвращающий панель товаров
     *
     * @return JPanel
     */
    public JPanel getPanel() {
        return this.menuPanel;
    }

    /**
     * Метод, устанавливающий слушателя на таблицу
     */
    private void setTableListener() {
        final ListSelectionModel lsm = prodListTable.getSelectionModel();
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
    }

    /**
     * Меняет таблицу товаров в зависимости от результатов поиска или выбора
     * всех товаров
     */
    private synchronized void getContentPanel() {
        indSelectedProd = 0;
        viewPanel.removeAll();
        try {
            new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        if (!flagsearch) {
                            prodList = prodComp.getAllProduct();
                        } else {
                            prodList = prodComp.searchProduct(searchStr);
                        }
                    } catch (DBException e) {
                        JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                    }
                    return null;
                }

                @Override
                public void done() {
                    if (!flagsearch) {
                        label = "<html><font color=blue>Список товаров";
                        prodTableLabel.setText(label);
                        back = new JPanel();
                        back.add(prodTableLabel, SwingConstants.CENTER);
                    } else {
                        label = "<html><font color=blue size = +1>Результаты поиска для '" + searchStr + "'";
                        JButton backButt = new JButton("<html><font color=blue>К списку всех товаров");
                        backButt.setFont(new Font("Verdana", Font.ITALIC, 15));
                        back = new JPanel(new GridLayout(1, 3));
                        JPanel jp = new JPanel(new GridLayout(1, 2));
                        jp.add(backButt);
                        jp.add(new JPanel());
                        back.add(jp);
                        prodTableLabel.setText(label);
                        back.add(prodTableLabel);
                        back.add(new JPanel());
                        backButt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                flagsearch = false;
                                getContentPanel();
                                viewPanel.removeAll();
                            }
                        });
                    }

                    ProdTableModel prodTable = new ProdTableModel();
                    prodListTable = prodTable.getTable(prodList);
                    JScrollPane jsp = new JScrollPane(prodListTable);
                    prodListTable.setPreferredScrollableViewportSize(new Dimension(1000, 800));
                    prodTablePanel.removeAll();
                    prodTablePanel.setVisible(true);
                    prodTableLabel.setFont(new Font("Verdana", Font.BOLD, 20));
                    prodTablePanel.add(back, BorderLayout.PAGE_START);
                    prodTablePanel.add(jsp);
                    prodTablePanel.repaint();
                    prodTablePanel.revalidate();
                    setTableListener();
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Метод, выполняющий поиск товара и возвращающий найденное в таблицу
     */
    private void search() {
        searchStr = searchText.getText();
        indSelectedProd = 0;
        if (searchStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Введите параметры поиска");
        } else {
            flagsearch = true;
            getContentPanel();
        }
    }

    /**
     * Метод, выполняющий удаление товара. Выдает предупреждения.
     *
     */
    private void deleteSelectedProduct() {
        if (ProductsPanel.indSelectedProd == 0) {
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
                            JOptionPane.showMessageDialog(null, "Удалено");
                        } catch (DBException e) {
                            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    public void done() {
                        getContentPanel();
                    }
                }.execute();
            } catch (Exception e) {
                Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void previewProductOnSouthBorder() {
        Image image = null;
        try {
            selectedFile = (String) prodListTable.getValueAt(indStr, 7);
            if (selectedFile == null || selectedFile.equals("null")) {
                image = ImageIO.read(new File("img\\noImage.jpg"));// Если null то изобр. по умолчанию
            } else {
                image = ImageIO.read(new File(selectedFile));
            }
        } catch (IOException ex) {
            try {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } catch (IOException ex1) {
                Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Ужимание товара до нужного размера
        image = image.getScaledInstance(200, -100, Image.SCALE_SMOOTH);
        image = image.getScaledInstance(-100, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        JPanel prodTextPan = new JPanel(new GridLayout(6, 1));
        prodTextPan.add(new JLabel("<html><font color=blue size=+2>  Название товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 1) + "</font></html>"));
        prodTextPan.add(new JLabel("<html><font color=blue size=+2>  Категория товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 2) + "</font></html>"));
        prodTextPan.add(new JLabel("<html><font color=blue size=+2>  Производитель товара: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 3) + "</font></html>"));
        prodTextPan.add(new JLabel("<html><font color=blue size=+2>  Цена товара: </font><font size =+1>" + prodListTable.getValueAt(indStr, 4).toString() + "</font></html>"));
        prodTextPan.add(new JLabel("<html><font color=blue size=+2>  Наличие: </font><font size =+1>" + (String) prodListTable.getValueAt(indStr, 6) + "</font></html>"));
        viewPanel.removeAll();
        viewPanel.add(prodTextPan, BorderLayout.WEST);
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
                    getContentPanel();
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void updateProduct() {
        try {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    updateProdDialog = new UpdateDialog(getSelectedProdView()).createDialog();
                    return null;
                }

                @Override
                public void done() {
                    getContentPanel();
                }
            }.execute();
        } catch (Exception e) {
            Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
