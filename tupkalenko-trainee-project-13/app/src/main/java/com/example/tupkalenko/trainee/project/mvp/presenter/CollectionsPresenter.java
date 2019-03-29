package com.example.tupkalenko.trainee.project.mvp.presenter;
import android.util.Log;

import com.example.tupkalenko.trainee.project.domain.entity.City;
import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.exception.NoCityFoundException;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.CollectionsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;
import com.example.tupkalenko.trainee.project.exception.NoCollectionsFoundException;
import com.example.tupkalenko.trainee.project.mvp.BasePresenter;
import com.example.tupkalenko.trainee.project.mvp.contract.CollectionsContract;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class CollectionsPresenter
       extends BasePresenter<CollectionsContract.CollectionsView, CollectionsScreenRouter>
       implements CollectionsContract.CollectionsPresenter {

    private final static String TAG = CollectionsPresenter.class.getName();

    @NonNull
    private final CityRepository cityRepository;

    @NonNull
    private final CollectionRepository collectionRepository;

    public CollectionsPresenter(@NonNull Scheduler backgroundScheduler,
                                @NonNull Scheduler foregroundScheduler,
                                @NonNull CollectionsScreenRouter router,
                                @NonNull CollectionRepository collectionRepository,
                                @NonNull CityRepository cityRepository) {
        super(backgroundScheduler, foregroundScheduler, router);
        this.collectionRepository = collectionRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void search(@NonNull String cityName) {
        Single<List<Collection>> chain = cityRepository.getCity(cityName)
                                                       .map(City::getId)
                                                       .flatMap(collectionRepository::
                                                                getCollectionsByCityId);

        subscribeWithProgress(chain, this::onSuccess, this::onError, showLoading(), hideLoading());
    }

    @Override
    public void searchByCurrentCoordinates() {
        //TODO retrieve real coordinates
        Single<List<Collection>> chain = cityRepository.getCityByCoordinates(1, 1)
                                                       .map(City::getId)
                                                       .flatMap(collectionRepository::
                                                               getCollectionsByCityId);

        subscribeWithProgress(chain, this::onSuccess, this::onError, showLoading(), hideLoading());
    }

    @Override
    public void showRestaurantsScreen(@NonNull Collection collection) {
        getRouter().showRestaurantsScreen(collection);
    }

    private void onSuccess(@NonNull List<Collection> collections) {
        if (isViewAttached()) {
            getView().onCollectionsLoaded(collections);
        }
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        Log.e(TAG, "error occured", throwable);
        if (!isViewAttached()) {
            return;
        }
        getView().onCollectionsLoaded(Collections.emptyList());
        if (throwable instanceof NoCityFoundException) {
            getView().showNoSuchCity();
        } else if (throwable instanceof NoCollectionsFoundException) {
            getView().showNoCollections();
        } else {
            super.onError(throwable);
        }
    }
}