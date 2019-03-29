package com.example.tupkalenko.trainee.project.mvp.contract;

import com.example.tupkalenko.trainee.project.mvp.BaseContract;

public interface MainContract {

    interface MainView extends BaseContract.BaseView<MainPresenter> {

    }

    interface MainPresenter extends BaseContract.BasePresenter<MainView> {

        void showCollectionsScreen();
    }
}