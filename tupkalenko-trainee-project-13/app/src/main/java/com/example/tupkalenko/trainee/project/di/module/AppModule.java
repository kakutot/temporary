package com.example.tupkalenko.trainee.project.di.module;

import com.example.tupkalenko.trainee.project.di.module.activity.ActivitiesModule;

import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = {AndroidInjectionModule.class,
                    ActivitiesModule.class,
                    RxModule.class,
                    RepositoryModule.class})
public abstract class AppModule {

}