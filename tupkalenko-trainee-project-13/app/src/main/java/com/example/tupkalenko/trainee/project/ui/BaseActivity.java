package com.example.tupkalenko.trainee.project.ui;

import android.os.Bundle;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.mvp.BaseContract;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<P extends BaseContract.BasePresenter>
       extends DaggerAppCompatActivity
       implements BaseContract.BaseView<P> {

    @Inject
    P presenter;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @NonNull
    protected P getPresenter() {
        return presenter;
    }

    @Override
    public void showUnexpectedError(@NonNull Throwable throwable) {
        new AlertDialog.Builder(this)
                       .setMessage(R.string.error_message_default)
                       .setPositiveButton(R.string.error_message_confirm, null)
                       .show();
    }
}