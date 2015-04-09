/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import com.tyrin.beans.Order;
import com.tyrin.exceptions.DBException;
import com.tyrin.swing.model.OrderTableModel;
import com.tyrin.services.IOrderService;
import com.tyrin.swing.util.Config;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author user
 */
public class OrdersPanel extends JPanel {

    public JMenuBar menu;
    private final JPanel main;//главная панель со всем твоим содержимым
    private final IOrderService order;
    private final List<Order> orderList; //Список всех заказов
    private final JTable orderListTable;
    private final JButton bDelete;
    public static int indStrOrder, indSelectedOrder;//Индекс строки и айди выбранного заказа

    public OrdersPanel() {
        order = Config.getServiceFactory().getOrderService();
        orderList = order.getAllOrders();
        bDelete = new JButton("Удалить заказ");
        menu = new Menu().getMenu();
        main = new JPanel(new BorderLayout());
        main.add(menu, BorderLayout.NORTH);
        main.add(bDelete, BorderLayout.SOUTH);
        orderListTable = createOrdersTable();
        JScrollPane jscrlp = new JScrollPane(orderListTable);
        main.add(jscrlp, BorderLayout.CENTER);
        setTableListener();

        // слушатель кнопки "удалить заказ"
        bDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedOrder();
            }
        });

    }

    private JTable createOrdersTable() {
        JTable table;
        TableModel model = new OrderTableModel(orderList);
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return table;
    }

    private void deleteSelectedOrder() {
        if (OrdersPanel.indSelectedOrder == 0) {
            JOptionPane.showMessageDialog(null, "Выберите заказ для удаления из списке");
            return;
        }
        int rez = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить выбранный заказ?");
        if (rez == JOptionPane.YES_OPTION) {
            try {
                new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            order.delOrder(indSelectedOrder);
                            JOptionPane.showMessageDialog(null, "Удалено");
                        } catch (DBException e) {
                            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка сервера " + e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    public void done() {
                    }
                }.execute();
            } catch (Exception e) {
                Logger.getLogger(ProductsPanel.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void setTableListener() {
        final ListSelectionModel lsm = orderListTable.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                ListSelectionModel l = (ListSelectionModel) e.getSource();
                // Номер текущей строки таблицы
                if (!l.isSelectionEmpty()) {
                    indStrOrder = l.getMinSelectionIndex();
                }
                if (indStrOrder != -1) {
                    indSelectedOrder = (int) orderListTable.getValueAt(indStrOrder, 0);
                }

            }
        });

        orderListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if (e.getClickCount() == 2) {
                    indSelectedOrder = (int) orderListTable.getValueAt(row, 0);
                }
            }
        });
    }

    public JPanel getPanel() {
        return this.main;
    }
}
