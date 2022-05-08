package com.example.firstproject.entities;

public class Order {

    private int id;
    private String goodId, tradePointId;
    private Double count, fullPrice;


    public Order(int id, String goodId, String tradePointId, Double count, Double fullPrice) {
        this.id = id;
        this.goodId = goodId;
        this.tradePointId = tradePointId;
        this.count = count;
        this.fullPrice = fullPrice;
    }

    public Order(String goodId, String tradePointId, Double count, Double fullPrice) {
        this.tradePointId = tradePointId;
        this.goodId = goodId;
        this.count = count;
        this.fullPrice = fullPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public String getTradePointId() {
        return tradePointId;
    }

    public double getCount() {
        return count;
    }

    public double getFullPrice() {
        return fullPrice;
    }
}
