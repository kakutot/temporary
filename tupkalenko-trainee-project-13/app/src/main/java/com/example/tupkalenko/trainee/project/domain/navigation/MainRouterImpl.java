package com.example.tupkalenko.trainee.project.domain.navigation;

import com.example.tupkalenko.trainee.project.domain.navigation.contract.MainRouter;
import com.example.tupkalenko.trainee.project.ui.fragment.CollectionsFragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainRouterImpl extends BaseRouter implements MainRouter {

    public MainRouterImpl(@NonNull AppCompatActivity activity, @IdRes int frameId) {
        super(activity, frameId);
    }

    @Override
    public void showCollectionsScreen() {
        replace(CollectionsFragment.getInstance());
    }

    @Override
    public void navigateBack() {
        // none
    }
}