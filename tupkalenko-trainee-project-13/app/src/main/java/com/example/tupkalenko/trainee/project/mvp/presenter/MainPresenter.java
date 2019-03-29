package com.example.tupkalenko.trainee.project.mvp.presenter;

import com.example.tupkalenko.trainee.project.domain.navigation.contract.MainRouter;
import com.example.tupkalenko.trainee.project.mvp.BasePresenter;
import com.example.tupkalenko.trainee.project.mvp.contract.MainContract;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;

public class MainPresenter
        extends BasePresenter<MainContract.MainView, MainRouter>
        implements MainContract.MainPresenter {

    public MainPresenter(@NonNull Scheduler backgroundScheduler,
                         @NonNull Scheduler foregroundScheduler,
                         @NonNull MainRouter router) {
        super(backgroundScheduler, foregroundScheduler, router);
    }

    @Override
    public void showCollectionsScreen() {
        getRouter().showCollectionsScreen();
    }
}