package com.example.tupkalenko.trainee.project.di.module.activity;

import com.example.tupkalenko.trainee.project.di.scopes.Activity;
import com.example.tupkalenko.trainee.project.ui.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivitiesModule {

    @Activity
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    MainActivity mainActivityInjector();
}