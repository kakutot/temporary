package com.example.tupkalenko.trainee.project.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.mvp.contract.CollectionsContract;
import com.example.tupkalenko.trainee.project.ui.BaseFragment;

import com.example.tupkalenko.trainee.project.ui.EmptyRecyclerViewObserver;
import com.example.tupkalenko.trainee.project.ui.adapter.CollectionsAdapter;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class CollectionsFragment
       extends BaseFragment<CollectionsContract.CollectionsPresenter>
       implements CollectionsContract.CollectionsView,
                  CollectionsAdapter.OnCollectionClickListener {

    @NonNull
    private final CollectionsAdapter collectionsAdapter = new CollectionsAdapter(this);

    @Nullable
    private EmptyRecyclerViewObserver<Collection> emptyRecyclerViewObserver;

    @BindView(R.id.collectionsSwipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.collectionsRV)
    RecyclerView collectionsRv;

    @BindView(R.id.collectionsEmptyTv)
    TextView emptyTv;

    @Nullable
    SearchView searchView;

    @NonNull
    public static CollectionsFragment getInstance() {
        return new CollectionsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareCollectionsRv();
        configureSwipeRefreshLayout();
        getPresenter().searchByCurrentCoordinates();
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (searchView != null) {
                String query = searchView.getQuery().toString();
                if (query.isEmpty()) {
                    getPresenter().searchByCurrentCoordinates();
                } else {
                    getPresenter().search(query);
                }
            }
        });
    }

    private void prepareCollectionsRv() {
        emptyRecyclerViewObserver = new EmptyRecyclerViewObserver(emptyTv, collectionsAdapter);
        //Default behaviour
        emptyRecyclerViewObserver.setCurrentText(getString(R.string.no_such_city));
        collectionsAdapter.registerAdapterDataObserver(emptyRecyclerViewObserver);
        collectionsRv.setAdapter(collectionsAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menu.findItem(R.id.action_search_collections).getActionView();
        MenuItem menuItem = menu.findItem(R.id.action_search_collections);
        menuItem.setActionView(searchView);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                                      MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menuItem.isActionViewExpanded();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String cityName) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (!searchView.isIconified()) {
                        if (cityName.isEmpty()) {
                            getPresenter().searchByCurrentCoordinates();
                        } else  {
                            getPresenter().search(cityName);
                        }
                    }
                });

                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        collectionsAdapter.unregisterAdapterDataObserver(emptyRecyclerViewObserver);
    }

    @Override
    public void onCollectionsLoaded(@NonNull List<Collection> collections) {
        collectionsAdapter.swapData(collections);
    }

    @Override
    public void showNoSuchCity() {
        String message = getResources().getString(R.string.no_such_city);
        emptyRecyclerViewObserver.setCurrentText(message);
    }

    @Override
    public void showNoCollections() {
        String message = getResources().getString(R.string.no_collections);
        emptyRecyclerViewObserver.setCurrentText(message);
    }

    @LayoutRes
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collections;
    }

    @StringRes
    @Override
    protected int getTitle() {
        return R.string.collections_title;
    }

    @MenuRes
    @Override
    protected int getMenuRes() {
        return R.menu.menu_collections;
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCollectionClick(@NonNull Collection collection) {
        getPresenter().showRestaurantsScreen(collection);
    }
}