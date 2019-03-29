package com.example.tupkalenko.trainee.project.di.module.fragment;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.di.qualifier.Background;
import com.example.tupkalenko.trainee.project.di.qualifier.Foreground;
import com.example.tupkalenko.trainee.project.di.scopes.Fragment;
import com.example.tupkalenko.trainee.project.domain.navigation.CollectionsScreenRouterImpl;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.CollectionsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.CollectionsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.CollectionsPresenter;
import com.example.tupkalenko.trainee.project.ui.activity.MainActivity;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public class CollectionsFragmentModule {

    @Fragment
    @Provides
    CollectionsContract.CollectionsPresenter presenter(@NonNull @Background
                                                       Scheduler backgroundScheduler,
                                                       @NonNull @Foreground
                                                       Scheduler foregroundScheduler,
                                                       @NonNull CollectionsScreenRouter router,
                                                       @NonNull CollectionRepository collectionRepository,
                                                       @NonNull CityRepository cityRepository) {

        return new CollectionsPresenter(backgroundScheduler,
                                        foregroundScheduler,
                                        router,
                                        collectionRepository,
                                        cityRepository);
    }

    @Fragment
    @Provides
    CollectionsScreenRouter router(MainActivity mainActivity) {
        return new CollectionsScreenRouterImpl(mainActivity, R.id.contentFl);
    }
}