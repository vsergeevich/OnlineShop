/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.beans;

import java.util.List;

/**
 *
 * @author Tyrin 
 */
public class Order {
    private List<Integer> listProductsId;
    private int id;
    private String fio;
    private String mail;
    private String phone;
    private String adress;

    public Order(List<Integer> listProductsId, int id, String fio, String mail, String phone, String adress) {
        this.listProductsId = listProductsId;
        this.id = id;
        this.fio = fio;
        this.mail = mail;
        this.phone = phone;
        this.adress = adress;
    }
    public Order() {
        this.listProductsId = null;
        this.id = 0;
        this.fio = "";
        this.mail = "";
        this.phone = "";
        this.adress = "";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
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
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public void setListProductsId(List<Integer> listProductsId) {
        this.listProductsId = listProductsId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Integer> getListProductsId() {
        return listProductsId;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdress() {
        return adress;
    }

}
