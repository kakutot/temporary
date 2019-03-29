package com.example.tupkalenko.trainee.project.domain.navigation;

import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantsScreenRouter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantsScreenRouterImpl
       extends BaseRouter
       implements RestaurantsScreenRouter {

    public RestaurantsScreenRouterImpl(@NonNull AppCompatActivity activity, int frameId) {
        super(activity, frameId);
    }

    @Override
    public void showRestaurantDetails(@NonNull Restaurant restaurant) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void navigateBack() {
       getActivity().onBackPressed();
    }
}
