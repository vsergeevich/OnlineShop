/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.beans;


/**
 *
 * @author Tyrin V. S.
 */
public class Product{

    
    private int id;
    private int catId;
    private int manId;
    private String name;
    private double price;
    private String desc;
    private int available;
    private String image;

    public Product(int id, int catId, int manId, String name, double price, String desc, int available, String image) {
        this.id = id;
        this.catId = catId;
        this.manId = manId;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.available = available;
        this.image = image;
    }

    public Product() {
        this.id = 0;
        this.catId = 0;
        this.manId = 0;
        this.name = null;
        this.price = 0;
        this.desc = null;
        this.available = 0;
        this.image = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setManId(int manId) {
        this.manId = manId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getCatId() {
        return catId;
    }

    public int getManId() {
        return manId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public int getAvailable() {
        return available;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + this.catId;
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
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", catId=" + catId + ", manId=" + manId + ", name=" + name + ", price=" + price + ", desc=" + desc + ", available=" + available + ", image=" + image + '}';
    }
}
