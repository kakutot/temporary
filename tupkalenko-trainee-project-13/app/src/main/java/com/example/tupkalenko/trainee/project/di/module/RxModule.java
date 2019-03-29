package com.example.tupkalenko.trainee.project.di.module;

import com.example.tupkalenko.trainee.project.di.qualifier.Background;
import com.example.tupkalenko.trainee.project.di.qualifier.Foreground;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class RxModule {

    @Background
    @Provides
    Scheduler backgroundScheduler() {
        return Schedulers.computation();
    }

    @Foreground
    @Provides
    Scheduler foregroundScheduler() {
        return AndroidSchedulers.mainThread();
    }
}