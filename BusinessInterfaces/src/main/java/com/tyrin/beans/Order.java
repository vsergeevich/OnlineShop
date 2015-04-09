/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.beans;

import java.util.Objects;

/**
 *
 * @author fits-dev
 */
public class Order {
    private int orderID;
    private int productID;
    private String fio;
    private String mail;
    private String phone;
    private String adress;

    public Order(int orderID, int productID, String fio, String mail, String phone, String adress) {
        this.orderID = orderID;
        this.productID = productID;
        this.fio = fio;
        this.mail = mail;
        this.phone = phone;
        this.adress = adress;
    }

    public Order(Order order) {
        this.orderID = order.orderID;
        this.productID = order.productID;
        this.fio = order.phone;
        this.mail = order.mail;
        this.phone = order.phone;
        this.adress = order.adress;
    }
    
    public Order() {
        this.orderID = 0;
        this.productID = 0;
        this.fio = null;
        this.mail = null;
        this.phone = null;
        this.adress = null;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.orderID;
        hash = 37 * hash + this.productID;
        hash = 37 * hash + Objects.hashCode(this.fio);
        hash = 37 * hash + Objects.hashCode(this.mail);
        hash = 37 * hash + Objects.hashCode(this.phone);
        hash = 37 * hash + Objects.hashCode(this.adress);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        if (!Objects.equals(this.fio, other.fio)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.adress, other.adress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", productID=" + productID + ", fio=" + fio + ", mail=" + mail + ", phone=" + phone + ", adress=" + adress + '}';
    }
    
    
    
    
}
