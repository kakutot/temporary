package com.example.tupkalenko.trainee.project.domain.navigation;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.CollectionsScreenRouter;
import com.example.tupkalenko.trainee.project.ui.fragment.RestaurantsFragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CollectionsScreenRouterImpl
       extends BaseRouter
       implements CollectionsScreenRouter {

    public CollectionsScreenRouterImpl(@NonNull AppCompatActivity activity, @IdRes int frameId) {
        super(activity, frameId);
    }

    @Override
    public void showRestaurantsScreen(@NonNull Collection collection) {
        add(RestaurantsFragment.getInstance(collection));
    }

    @Override
    public void navigateBack() {
       // none
    }
}