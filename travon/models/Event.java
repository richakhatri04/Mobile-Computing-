package com.thealienobserver.nikhil.travon.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Event {
    String name;
    String description;
    String url;
    String imageUrl;
    Date startTime;
    Date endTime;
    boolean isFree;
    LatLng location;
    String address;

    public Event(String name, String description, String url, String imageUrl,
                 Date startTime, Date endTime, boolean isFree, LatLng location, String address) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isFree = isFree;
        this.location = location;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isFree() {
        return isFree;
    }

    public LatLng getLocation() { return location; }

    public String getAddress() { return address; }

}
