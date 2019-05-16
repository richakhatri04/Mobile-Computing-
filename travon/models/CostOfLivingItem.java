package com.thealienobserver.nikhil.travon.models;

public class CostOfLivingItem {
    private String item_name;
    private double lowest_price;
    private double average_price;
    private double highest_price;


    public CostOfLivingItem(String item_name, double lowest_price, double average_price, double highest_price) {
        this.item_name = item_name;
        this.lowest_price = lowest_price;
        this.average_price = average_price;
        this.highest_price = highest_price;
    }

    public String getItemName() {
        return item_name;
    }

    public double getLowestPrice() {
        return lowest_price;
    }

    public double getAveragePrice() {
        return average_price;
    }

    public double getHighestPrice() {
        return highest_price;
    }

}

