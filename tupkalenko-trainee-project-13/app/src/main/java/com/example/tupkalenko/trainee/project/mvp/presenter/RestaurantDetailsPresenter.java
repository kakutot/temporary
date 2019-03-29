package com.example.tupkalenko.trainee.project.mvp.presenter;

import android.util.Log;

import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.exception.NoRestaurantFoundException;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantDetailsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.BasePresenter;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantDetailsContract;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class RestaurantDetailsPresenter
        extends BasePresenter<RestaurantDetailsContract.RestaurantsDetailsView, RestaurantDetailsScreenRouter>
        implements RestaurantDetailsContract.RestaurantDetailsPresenter {

    private final static String TAG = RestaurantDetailsPresenter.class.getName();

    @NonNull
    private final RestaurantRepository restaurantRepository;

    public RestaurantDetailsPresenter(@NonNull Scheduler backgroundScheduler,
                                      @NonNull Scheduler foregroundScheduler,
                                      @NonNull RestaurantDetailsScreenRouter router,
                                      @NonNull RestaurantRepository restaurantRepository) {
        super(backgroundScheduler, foregroundScheduler, router);
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void loadDetails(@NonNull Restaurant restaurant) {
        Single chain = restaurantRepository.getRestaurant(restaurant.getId());

        subscribeWithProgress(chain, this::onSuccess, super::onError, showLoading(), hideLoading());
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        if (!isViewAttached()) {
            Log.d(TAG, "view is not attached");
            return;
        }
        if (throwable instanceof NoRestaurantFoundException) {
            getView().showNoSuchRestaurantException();
        } else {
            super.onError(throwable);
        }
    }

    private void onSuccess(@NonNull Restaurant restaurant) {
        if (isViewAttached()) {
            getView().onRestaurantLoaded(restaurant);
        }
    }
}