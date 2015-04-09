package com.tyrin.beans;

/**
 *
 * @author Tyrin V. S.
 */
public class Manufacturer {

    private int id;
    private String name;
    private String country;

    public Manufacturer(int manId, String prodMan, String country) {
        this.id = manId;
        this.name = prodMan;
        this.country = country;
    }

    public Manufacturer(Manufacturer man) {
        this.id = man.id;
        this.name = man.name;
        this.country = man.country;
    }

    public Manufacturer() {
        this.id = 0;
        this.name = null;
        this.country = null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Manufacturer other = (Manufacturer) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Manufacturer{" + "id=" + id + ", name=" + name + '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

}
