package com.example.tupkalenko.trainee.project.mvp.contract;

import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.mvp.BaseContract;

import androidx.annotation.NonNull;

public interface RestaurantDetailsContract {

    interface RestaurantsDetailsView extends
            BaseContract.BaseView<RestaurantDetailsPresenter> {

        void onRestaurantLoaded(@NonNull Restaurant restaurant);

        void showNoSuchRestaurantException();
    }

    interface RestaurantDetailsPresenter
            extends BaseContract.BasePresenter<RestaurantsDetailsView> {

        void loadDetails(@NonNull Restaurant restaurant);
    }
}