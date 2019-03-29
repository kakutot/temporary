package com.example.tupkalenko.trainee.project.mvp.presenter;

import android.util.Log;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.BasePresenter;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantsContract;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RestaurantsPresenter
        extends BasePresenter<RestaurantsContract.RestaurantsView, RestaurantsScreenRouter>
        implements RestaurantsContract.RestaurantsPresenter {

    private final static String TAG = RestaurantsPresenter.class.getName();

    @NonNull
    private final RestaurantRepository restaurantRepository;

    public RestaurantsPresenter(@NonNull Scheduler backgroundScheduler,
                                @NonNull Scheduler foregroundScheduler,
                                @NonNull RestaurantsScreenRouter router,
                                @NonNull RestaurantRepository restaurantRepository) {
        super(backgroundScheduler, foregroundScheduler, router);
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void search(@NonNull String restaurantName,
                       @NonNull Collection collection,
                       int start) {
        if (start >= 0) {
            Single chain = restaurantRepository.getRestaurantsByCollectionId(restaurantName,
                                                                             collection.getId(),
                                                                             start);

            Log.e(TAG, "SEARCHING ");

            if (start !=0) {
                getView().showLoadingMoreItems();
                subscribeWithProgress(chain,
                                     this::onSuccessWithPaging,
                                     this::onError,
                                     showLoadingMoreItems(),
                                     hideLoadingMoreItems());

            } else {
                subscribeWithProgress(chain,
                                      this::onSuccess,
                                      this::onError,
                                      showLoading(),
                                      hideLoading());
            }
        } else {
            Log.e(TAG, "Bad start value : " + start);
        }
    }

    @NonNull
    private Consumer<Disposable> showLoadingMoreItems() {
        return disposable -> {
            if (!isViewAttached()) {
                return;
            }
            getView().showLoadingMoreItems();
        };
    }

    @NonNull
    public Action hideLoadingMoreItems() {
        return () -> {
            if (!isViewAttached()) {
                return;
            }
            getView().hideLoadingMoreItems();
        };
    }

    private void onSuccess(@NonNull List<Restaurant> restaurants) {
        Log.e(TAG, "Result" + restaurants.size());
        if (isViewAttached()) {
            boolean hasMore = restaurants.size() > 0;
            getView().setHasMore(hasMore);
            getView().hideLoadingMoreItems();
            getView().onRestaurantsListLoaded(restaurants);
        }
    }

    private void onSuccessWithPaging(@NonNull List<Restaurant> restaurants) {
        Log.e(TAG, "Result" + restaurants.size());
        if (isViewAttached()) {
            boolean hasMore = restaurants.size() > 0;
            getView().setHasMore(hasMore);
            getView().hideLoadingMoreItems();
            getView().onRestaurantsListPageLoaded(restaurants);
        }
    }


    @Override
    public void showRestaurantDetails(@NonNull Restaurant restaurant) {
        getRouter().showRestaurantDetails(restaurant);
    }
}