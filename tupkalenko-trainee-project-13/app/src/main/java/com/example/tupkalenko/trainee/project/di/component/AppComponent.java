package com.example.tupkalenko.trainee.project.di.component;

import com.example.tupkalenko.trainee.project.Application;
import com.example.tupkalenko.trainee.project.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> {

    }
}