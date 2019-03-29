package com.example.tupkalenko.trainee.project.domain.repository;

import com.example.tupkalenko.trainee.project.domain.entity.City;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface CityRepository {

    @NonNull
    Single<City> getCity(@NonNull String cityName);

    @NonNull
    Single<City> getCityByCoordinates(double lat, double lng);
}