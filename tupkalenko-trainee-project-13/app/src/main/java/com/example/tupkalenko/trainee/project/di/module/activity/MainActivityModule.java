package com.example.tupkalenko.trainee.project.di.module.activity;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.di.qualifier.Background;
import com.example.tupkalenko.trainee.project.di.qualifier.Foreground;
import com.example.tupkalenko.trainee.project.di.scopes.Activity;
import com.example.tupkalenko.trainee.project.domain.navigation.MainRouterImpl;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.MainRouter;
import com.example.tupkalenko.trainee.project.mvp.contract.MainContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.MainPresenter;
import com.example.tupkalenko.trainee.project.ui.activity.MainActivity;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = {MainActivityFragmentProvider.class})
public abstract class MainActivityModule {

    @Activity
    @Provides
    static MainContract.MainPresenter presenter(@NonNull @Background
                                                Scheduler backgroundScheduler,
                                                @NonNull @Foreground
                                                Scheduler foregroundScheduler,
                                                @NonNull MainRouter router) {
        
        return new MainPresenter(backgroundScheduler, foregroundScheduler, router);
    }

    @Activity
    @Provides
    static MainRouter router(MainActivity activity) {
        return new MainRouterImpl(activity, R.id.contentFl);
    }
}