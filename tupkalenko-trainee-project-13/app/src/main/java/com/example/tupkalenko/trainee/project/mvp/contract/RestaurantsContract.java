package com.example.tupkalenko.trainee.project.mvp.contract;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.mvp.BaseContract;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface RestaurantsContract {

    interface RestaurantsView
            extends BaseContract.BaseView<RestaurantsPresenter> {

        void onRestaurantsListLoaded(@NonNull List<Restaurant> Restaurants);

        void onRestaurantsListPageLoaded(@NonNull List<Restaurant> Restaurants);

        void showLoadingMoreItems();

        void hideLoadingMoreItems();

        void setHasMore(boolean hasMore);
    }

    interface RestaurantsPresenter
            extends BaseContract.BasePresenter<RestaurantsView> {

        void search(@Nullable String restaurantName, @NonNull Collection collection, int start);

        void showRestaurantDetails(@NonNull Restaurant restaurant);
    }
}