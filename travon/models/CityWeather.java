package com.thealienobserver.nikhil.travon.models;

public class CityWeather {
    double temperature;
    String description, iconUrl, city;
    double humidity, tempMin, tempMax;
    int clouds;

    public CityWeather(String city, double temperature, String description, double humidity, double tempMin, double tempMax, int clouds, String iconUrl) {
        this.city=city;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.clouds = clouds;
        this.iconUrl = iconUrl;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCity() { return city; }
    public String getDescription() {
        return description;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public int getClouds() {
        return clouds;
    }

    public String getIconUrl() { return iconUrl; }
}
