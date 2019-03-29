package com.example.tupkalenko.trainee.project.di.module;

import com.example.tupkalenko.trainee.project.domain.mock.MockCityRepository;
import com.example.tupkalenko.trainee.project.domain.mock.MockCollectionRepository;
import com.example.tupkalenko.trainee.project.domain.mock.MockRestaurantRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    CityRepository cityRepository() {
        return new MockCityRepository();
    }

    @Singleton
    @Provides
    CollectionRepository collectionRepository() {
        return new MockCollectionRepository();
    }

    @Singleton
    @Provides
    RestaurantRepository restaurantRepository() {
        return new MockRestaurantRepository();
    }
}