package com.example.tupkalenko.trainee.project.di.module.fragment;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.di.qualifier.Background;
import com.example.tupkalenko.trainee.project.di.qualifier.Foreground;
import com.example.tupkalenko.trainee.project.di.scopes.Fragment;
import com.example.tupkalenko.trainee.project.domain.navigation.RestaurantDetailsScreenRouterImpl;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantDetailsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantDetailsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.RestaurantDetailsPresenter;
import com.example.tupkalenko.trainee.project.ui.activity.MainActivity;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public abstract class RestaurantDetailsFragmentModule {

    @Fragment
    @Provides
    RestaurantDetailsContract.RestaurantDetailsPresenter presenter(@NonNull @Background
                                                                   Scheduler backgroundScheduler,
                                                                   @NonNull @Foreground
                                                                   Scheduler foregroundScheduler,
                                                                   @NonNull
                                                                   RestaurantDetailsScreenRouter router,
                                                                   @NonNull
                                                                   RestaurantRepository restaurantRepository) {

        return new RestaurantDetailsPresenter(backgroundScheduler,
                                              foregroundScheduler,
                                              router,
                                              restaurantRepository);
    }

    @Fragment
    @Provides
    RestaurantDetailsScreenRouter router(MainActivity mainActivity){
        return new RestaurantDetailsScreenRouterImpl(mainActivity, R.id.contentFl);
    }
}
