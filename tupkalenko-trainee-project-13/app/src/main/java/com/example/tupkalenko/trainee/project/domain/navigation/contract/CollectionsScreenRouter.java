package com.example.tupkalenko.trainee.project.domain.navigation.contract;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;

import androidx.annotation.NonNull;

public interface CollectionsScreenRouter extends Router {

    void showRestaurantsScreen(@NonNull Collection collection);
}