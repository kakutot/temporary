package com.example.tupkalenko.trainee.project.domain.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Location {

    @Nullable
    private String address;

    @Nullable
    private String city;

    private double latitude;

    private double longitude;

    private int countryId;

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
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

    public double getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}