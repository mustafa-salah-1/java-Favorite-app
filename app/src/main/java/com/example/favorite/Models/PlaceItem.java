package com.example.favorite.Models;

public class PlaceItem {
    private String name;
    private String description;
    private double latitude;
    private double longitude;

    public PlaceItem(String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description != null ? description : "";
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
