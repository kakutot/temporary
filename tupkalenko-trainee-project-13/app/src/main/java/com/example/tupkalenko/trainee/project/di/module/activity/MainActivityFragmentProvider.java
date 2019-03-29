package com.example.tupkalenko.trainee.project.di.module.activity;

import com.example.tupkalenko.trainee.project.di.module.fragment.CollectionsFragmentModule;
import com.example.tupkalenko.trainee.project.di.module.fragment.RestaurantsFragmentModule;
import com.example.tupkalenko.trainee.project.di.scopes.Fragment;
import com.example.tupkalenko.trainee.project.ui.fragment.CollectionsFragment;
import com.example.tupkalenko.trainee.project.ui.fragment.RestaurantsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainActivityFragmentProvider {

    @Fragment
    @ContributesAndroidInjector(modules = {CollectionsFragmentModule.class})
    CollectionsFragment collectionsFragment();

    @Fragment
    @ContributesAndroidInjector(modules = {RestaurantsFragmentModule.class})
    RestaurantsFragment restaurantsFragment();
}
