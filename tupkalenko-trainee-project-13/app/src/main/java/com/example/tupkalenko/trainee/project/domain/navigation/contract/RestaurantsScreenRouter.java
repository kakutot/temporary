package com.example.tupkalenko.trainee.project.domain.navigation.contract;

import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;

import androidx.annotation.NonNull;

public interface RestaurantsScreenRouter extends Router {

    void showRestaurantDetails(@NonNull Restaurant restaurant);
}