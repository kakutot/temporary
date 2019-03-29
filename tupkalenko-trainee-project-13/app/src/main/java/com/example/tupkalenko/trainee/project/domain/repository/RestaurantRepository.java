package com.example.tupkalenko.trainee.project.domain.repository;

import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;

public interface RestaurantRepository {

    @NonNull
    Single<List<Restaurant>> getRestaurantsByCollectionId(@NonNull String restaurantName,
                                                          int collectionId,
                                                          int start);

    @NonNull
    Single<Restaurant> getRestaurant(int restaurantId);
}