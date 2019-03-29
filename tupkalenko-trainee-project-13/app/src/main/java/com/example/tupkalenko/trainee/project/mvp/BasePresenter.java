package com.example.tupkalenko.trainee.project.mvp;

import android.util.Log;

import com.example.tupkalenko.trainee.project.domain.navigation.contract.Router;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public abstract class BasePresenter<V extends BaseContract.BaseView, R extends Router>
        extends RxExecutor
        implements BaseContract.BasePresenter<V> {

    private final static String TAG = BasePresenter.class.getName();

    @NonNull
    private final R router;

    @Nullable
    private V view;

    public BasePresenter(@NonNull Scheduler backgroundScheduler,
                         @NonNull Scheduler foregroundScheduler,
                         @NonNull R router) {
        super(backgroundScheduler, foregroundScheduler);
        this.router = router;
    }

    @Override
    public void attachView(@NonNull V view) {
        this.view = view;
        initResources();
    }

    @Override
    public void detachView() {
        view = null;
        clearResources();
    }

    @NonNull
    @Override
    public V getView() {
        return Objects.requireNonNull(view);
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @NonNull
    @Override
    public R getRouter() {
        return router;
    }

    @Override
    public void navigateBack() {
        getRouter().navigateBack();
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        if (isViewAttached()) {
            getView().showUnexpectedError(throwable);
        }
        Log.e(TAG, "handled error", throwable);
    }

    @NonNull
    public Consumer<Disposable> showLoading() {
        return disposable -> {
            if (!isViewAttached()) {
                return;
            }
            getView().showLoading();
        };
    }

    @NonNull
    public Action hideLoading() {
        return () -> {
            if (!isViewAttached()) {
                return;
            }
            getView().hideLoading();
        };
    }
}