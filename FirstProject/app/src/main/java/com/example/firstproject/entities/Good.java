package com.example.firstproject.entities;

public class Good {

    private int id;
    private String name;
    private String measure;
    private double price;

    public Good(String name, String measure, double price) {
        this.name = name;
        this.measure = measure;
        this.price = price;
    }
    public Good(int id, String name, String measure, double price) {
        this.id = id;
        this.name = name;
        this.measure = measure;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public double getPrice() {
        return price;
    }
}
