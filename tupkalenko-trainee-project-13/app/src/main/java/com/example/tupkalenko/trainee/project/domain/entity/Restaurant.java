package com.example.tupkalenko.trainee.project.domain.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Restaurant {

    private int id;

    @Nullable
    private String name;

    @Nullable
    private Location location;

    private float averageCostForTwo;

    @Nullable
    private String currency;

    @Nullable
    private String featuredImage;

    @Nullable
    private UserRating userRating;

    private boolean isDeliveringNow;

    private boolean hasTableBooking;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    public void setLocation(@NonNull Location location) {
        this.location = location;
    }

    public float getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(float averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    @Nullable
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    @Nullable
    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(@NonNull String featuredImage) {
        this.featuredImage = featuredImage;
    }

    @Nullable
    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(@NonNull UserRating userRating) {
        this.userRating = userRating;
    }

    public boolean isDeliveringNow() {
        return isDeliveringNow;
    }

    public void setDeliveringNow(boolean deliveringNow) {
        isDeliveringNow = deliveringNow;
    }

    public boolean hasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(boolean hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }
}