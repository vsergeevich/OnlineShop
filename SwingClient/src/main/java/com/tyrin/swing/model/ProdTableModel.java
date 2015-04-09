/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.model;

import com.tyrin.beans.Category;
import com.tyrin.beans.Manufacturer;
import com.tyrin.beans.Product;
import com.tyrin.exceptions.DBException;
import com.tyrin.services.ICategoryService;
import com.tyrin.services.IManufacturerService;
import com.tyrin.swing.util.Config;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Tyrin V. S.
 * Класс, инкапсулирующий механизм построения таблицы товаров
 */
public class ProdTableModel {

    private JTable prodListTable;
    private IManufacturerService manComp;
    private ICategoryService catComp;
    private List<Category> catList;
    private List<Manufacturer> manList;

    public JTable getTable(final List<Product> prodList) {
        manComp = Config.getServiceFactory().getManufacturerService();
        catComp = Config.getServiceFactory().getCategoryService();
        prodListTable = new JTable();
        try {
            catList = catComp.getAllCategory(); // все категории и производители
            manList = manComp.getAllManufacturer();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка базы данных: " + e.getMessage());
            return prodListTable; // Если эксепшн - выдаем пустую таблицу
        }
        
        //Строим HashMap айди категории - название, для отбражения в таблице
        final HashMap<Integer, String> manHash = new HashMap<>();
        final HashMap<Integer, String> catHash = new HashMap<>();
        for (Manufacturer m : manList) {
            manHash.put(m.getId(), m.getName());
        }
        for (Category c : catList) {
            catHash.put(c.getId(), c.getName());
        }
        
//Локальный класс, описывающий модель таблицы
        class MyModel implements TableModel {

            private final Set<TableModelListener> listeners = new HashSet<TableModelListener>();

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return prodList.size();
            }

            @Override
            public int getColumnCount() {
                return 8;
            }

            @Override
            public String getColumnName(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return "Код";
                    case 1:
                        return "Название товара";
                    case 2:
                        return "Категория";
                    case 3:
                        return "Производитель";
                    case 4:
                        return "Цена";
                    case 5:
                        return "Описание";
                    case 6:
                        return "Наличие";
                    case 7:
                        return "Изображение";
                }
                return "";
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return  String.class;
                    case 3:
                        return String.class;
                    case 4:
                        return Double.class;
                    case 5:
                        return  String.class;
                    case 6:
                        return  String.class;
                    case 7:
                        return String.class;
                }
                return Object.class;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Product product = prodList.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return product.getId();
                    case 1:
                        return product.getName();
                    case 2:
                        return catHash.get(product.getCatId());
                    case 3:
                        return manHash.get(product.getManId());
                    case 4:
                        return product.getPrice();
                    case 5:
                        return product.getDesc();
                    case 6:
                        return product.getAvailable()== 1 ? "В наличии" : "Отсутствует";
                    case 7:
                        return product.getImage();
                }
                return "";
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            }

            @Override
            public void addTableModelListener(TableModelListener l) {
                listeners.add(l);
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
                listeners.remove(l);
            }
        }
        TableModel model = new MyModel();
        prodListTable = new JTable(model);
        prodListTable.getTableHeader().setReorderingAllowed(false);
        prodListTable.setAutoCreateRowSorter(true);
        prodListTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        prodListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prodListTable.getColumnModel().getColumn(0).setMinWidth(50);
        prodListTable.getColumnModel().getColumn(0).setMaxWidth(50);
        prodListTable.getColumnModel().getColumn(1).setMinWidth(170);
        prodListTable.getColumnModel().getColumn(1).setMaxWidth(250);
        prodListTable.getColumnModel().getColumn(2).setMinWidth(120);
        prodListTable.getColumnModel().getColumn(2).setMaxWidth(200);
        prodListTable.getColumnModel().getColumn(3).setMinWidth(100);
        prodListTable.getColumnModel().getColumn(3).setMaxWidth(200);
        prodListTable.getColumnModel().getColumn(4).setMinWidth(60);
        prodListTable.getColumnModel().getColumn(4).setMaxWidth(100);
        prodListTable.getColumnModel().getColumn(5).setMinWidth(1);
        prodListTable.getColumnModel().getColumn(6).setMinWidth(40);
        prodListTable.getColumnModel().getColumn(6).setMaxWidth(70);
        prodListTable.getColumnModel().getColumn(7).setMaxWidth(200);

        return prodListTable;
    }
}
