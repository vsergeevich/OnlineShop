/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swing.model;

import com.tyrin.beans.Manufacturer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author lenovo
 */
public class ManufacturerTableModel implements TableModel {

    private final List<Manufacturer> manList;

    public ManufacturerTableModel(List<Manufacturer> manList) {
        this.manList = manList;
    }
    
    private final Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public int getRowCount() {
        return manList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Код";
            case 1:
                return "Производитель";
            case 2:
                return "Страна";
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
                return String.class;
        }
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Manufacturer man = manList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return man.getId();
            case 1:
                return man.getName();
            case 2:
                return man.getCountry();
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
