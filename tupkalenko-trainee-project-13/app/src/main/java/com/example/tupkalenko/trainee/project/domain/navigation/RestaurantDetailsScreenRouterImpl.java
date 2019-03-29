package com.example.tupkalenko.trainee.project.domain.navigation;

import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantDetailsScreenRouter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantDetailsScreenRouterImpl
       extends BaseRouter
       implements RestaurantDetailsScreenRouter {

    public RestaurantDetailsScreenRouterImpl(@NonNull AppCompatActivity activity, int frameId) {
        super(activity, frameId);
    }

    @Override
    public void navigateBack() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
