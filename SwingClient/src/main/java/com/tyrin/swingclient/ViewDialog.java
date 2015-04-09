/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.swingclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Tyrin V. S. Класс, описывающий окно просмотра товара
 */
public class ViewDialog extends JFrame {

    private JDialog updateDialog;
    private JPanel main;
    private JLabel name, category, manufacturer, price, avail;
    private final JLabel imageLabel;
    private Image image;
    private ImageIcon icon;

    /**
     * Конструктор класса получает Map, в которой содержиться описание
     * характеристик товара. Значения как в бине Product, но только вместо айди
     * категории и производителя - их названия
     *
     * @param map
     */
    public ViewDialog(final Map<String, String> map) {
        main = new JPanel(new BorderLayout());
        name = new JLabel();
        try {
            String nameLbl = "<html><table width=350>" + map.get("name") + "</html>";
            name = new JLabel(nameLbl);
            name.setFont(new Font("Verdana", Font.BOLD, 16));
            category = new JLabel("<html><font color=blue size=+1>Категория: </font><font size =+1>" + map.get("cat") + "</font></html>");
            manufacturer = new JLabel("<html><font color=blue size=+1>Производитель: </font><font size =+1>" + map.get("man") + "</font></html>");
            avail = new JLabel("<html><font color=blue size=+1>Наличие: </font><font size =+1>" + map.get("avail") + "</font></html>");
            price = new JLabel("<html><font color=blue size=+1>Цена: </font><font size =+1>" + map.get("price") + "</font></html>");
            if (map.get("image") == null || map.get("image").equals("null")) {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } else {
                image = ImageIO.read(new File(map.get("image")));
            }
        } catch (IOException ex) {
            try {
                image = ImageIO.read(new File("img\\noImage.jpg"));
            } catch (IOException ex1) {
                Logger.getLogger(ViewDialog.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        image = image.getScaledInstance(400, -100, Image.SCALE_SMOOTH);
        image = image.getScaledInstance(-100, 400, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        imageLabel = new JLabel(icon);
        JPanel center = new JPanel(new GridLayout(8, 1));
        JPanel namePanel = new JPanel();
        namePanel.add(name);
        center.add(namePanel, SwingConstants.CENTER);
        center.add(new JPanel());
        center.add(category);
        center.add(manufacturer);
        center.add(avail);
        center.add(price);
        JPanel updatePanel = new JPanel(new BorderLayout());
        JPanel okPanel = new JPanel(new BorderLayout());
        JButton updateButton = new JButton("Изменить");
        updateButton.setSize(30, 15);
        updatePanel.add(updateButton, BorderLayout.EAST);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                updateDialog = new UpdateDialog(map).createDialog();
            }
        });
        JButton okButton = new JButton("      OK      ");
        okButton.setMinimumSize(new Dimension(50, 50));
        okPanel.add(okButton, BorderLayout.EAST);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        center.add(updatePanel);
        center.add(okPanel);

        main.add(imageLabel, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
    }

    /**
     * Метод возвращающий экземпляр этого диалога
     *
     * @return JDialog
     */
    public JDialog createDialog() {
        JDialog dialog = new JDialog(this, "Просмотр товара", true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialog.setSize(420, 700);
        dialog.add(main);
        int X = Math.round((MainWindow.FRAME_WIDTH - 500) / 2);
        dialog.setLocation(X, 0);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }
}
