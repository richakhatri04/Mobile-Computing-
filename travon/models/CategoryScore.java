package com.thealienobserver.nikhil.travon.models;

public class CategoryScore {
    private String color;
    private String name;
    private double score_out_of_10;

    public CategoryScore(String color, String name, double score_out_of_10) {
        this.color = color;
        this.name = name;
        this.score_out_of_10 = score_out_of_10;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public double getScoreOutOf10() {
        return score_out_of_10;
    }
}
