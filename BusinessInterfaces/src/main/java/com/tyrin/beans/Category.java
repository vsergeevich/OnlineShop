/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tyrin.beans;

/**
 *
 * @author Tyrin V. S.
 * Entity категории
 */
public class Category{

    private int id;
    private int parentId;
    private String name;

    public Category(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Category(Category cat) {
        this.id = cat.id;
        this.parentId = cat.parentId;
        this.name = cat.name;
    }

    public Category() {
        this.id = 0;
        this.parentId = 0;
        this.name = null;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", parentId=" + parentId + ", name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + this.parentId;
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
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.parentId != other.parentId) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
