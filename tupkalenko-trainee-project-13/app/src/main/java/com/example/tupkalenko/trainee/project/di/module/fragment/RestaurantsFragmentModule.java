package com.example.tupkalenko.trainee.project.di.module.fragment;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.di.qualifier.Background;
import com.example.tupkalenko.trainee.project.di.qualifier.Foreground;
import com.example.tupkalenko.trainee.project.di.scopes.Fragment;
import com.example.tupkalenko.trainee.project.domain.navigation.RestaurantsScreenRouterImpl;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.RestaurantsPresenter;
import com.example.tupkalenko.trainee.project.ui.activity.MainActivity;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class RestaurantsFragmentModule {

    @Fragment
    @Provides
    RestaurantsContract.RestaurantsPresenter presenter(@NonNull @Background
                                                       Scheduler backgroundScheduler,
                                                       @NonNull @Foreground
                                                       Scheduler foregroundScheduler,
                                                       @NonNull
                                                       RestaurantsScreenRouter router,
                                                       @NonNull
                                                       RestaurantRepository restaurantRepository) {

        return new RestaurantsPresenter(backgroundScheduler,
                                        foregroundScheduler,
                                        router,
                                        restaurantRepository);
    }

    @Fragment
    @Provides
    RestaurantsScreenRouter router(MainActivity mainActivity) {
        return new RestaurantsScreenRouterImpl(mainActivity, R.id.contentFl);
    }
}
