package com.example.tupkalenko.trainee.project.ui;

import androidx.annotation.MenuRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.mvp.BaseContract;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<P extends BaseContract.BasePresenter>
       extends Fragment implements BaseContract.BaseView<P> {

    @Inject
    P presenter;

    @Nullable
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        if (withMenu()) {
            setHasOptionsMenu(true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        unbinder = ButterKnife.bind(this, view);
        getPresenter().attachView(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(getMenuRes(), menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getTitle());
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getPresenter().detachView();
    }

    @NonNull
    protected P getPresenter() {
        return presenter;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @StringRes
    protected abstract int getTitle();

    @Nullable
    public ActionBar getSupportActionBar() {
        return ((AppCompatActivity)getActivity()).getSupportActionBar();
    }

    @MenuRes
    protected int getMenuRes() {
        return 0;
    }

    private boolean withMenu() {
        return getMenuRes() != 0;
    }

    @Override
    public void showUnexpectedError(@NonNull Throwable throwable) {
        new AlertDialog.Builder(this.getActivity())
                       .setMessage(R.string.error_message_default)
                       .setPositiveButton(R.string.error_message_confirm, null)
                       .show();

        Log.e(this.getClass().getName(), Log.getStackTraceString(throwable));
    }
}