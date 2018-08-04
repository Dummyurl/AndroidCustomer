package com.samyotech.fabcustomer.firebase_model;

public class LocationModel {
    private String title;
    private String email;
    private double latitude;
    private double longitude;

    public LocationModel() {
    }


    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
