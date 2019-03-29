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

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantsContract;
import com.example.tupkalenko.trainee.project.ui.BaseFragment;
import com.example.tupkalenko.trainee.project.ui.EmptyRecyclerViewObserver;
import com.example.tupkalenko.trainee.project.ui.adapter.RestaurantsAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class RestaurantsFragment
       extends BaseFragment<RestaurantsContract.RestaurantsPresenter>
       implements RestaurantsContract.RestaurantsView,
                  RestaurantsAdapter.OnRestaurantClickListener {

    private final static String PARAM_COLLECTION = "param_collection";
    private static final String TAG = RestaurantsFragment.class.getName() ;

    @NonNull
    private final RestaurantsAdapter restaurantsAdapter = new RestaurantsAdapter(this);

    @Nullable
    private EmptyRecyclerViewObserver<Restaurant> emptyRecyclerViewObserver;

    @BindView (R.id.restaurantsSwipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.restaurantsRV)
    RecyclerView restaurantsRV;

    @BindView(R.id.restaurantsEmptyTv)
    TextView emptyTv;

    @Nullable
    SearchView searchView;

    public static RestaurantsFragment getInstance(@NonNull Collection collection) {
       RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
       Bundle arguments = new Bundle();
       arguments.putParcelable(PARAM_COLLECTION, collection);
       restaurantsFragment.setArguments(arguments);

       return restaurantsFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareRestaurantsRv();
        configureSwipeRefreshLayout();

        getPresenter().search("", getCollection(), 0);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (searchView != null) {
                String query = searchView.getQuery().toString();
                getPresenter().search(query, getCollection(), 0);
            }
        });
    }

    private void prepareRestaurantsRv() {
        emptyRecyclerViewObserver = new EmptyRecyclerViewObserver(emptyTv, restaurantsAdapter);
        //Default behaviour
        emptyRecyclerViewObserver.setCurrentText(getString(R.string.no_restaurants));
        restaurantsAdapter.registerAdapterDataObserver(emptyRecyclerViewObserver);
        restaurantsRV.setAdapter(restaurantsAdapter);
        restaurantsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)restaurantsRV.getLayoutManager();

                int childCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!restaurantsAdapter.isLoading() &&
                    childCount + firstVisibleItemPosition >= totalItemCount &&
                    firstVisibleItemPosition >= 0   && dy >0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("search : ").append("childcount : ").append(childCount)
                                 .append("/totalItemCount").append(totalItemCount)
                                 .append("/firstvisible: ").append(firstVisibleItemPosition);
                    Log.e(this.getClass().getName(),stringBuilder.toString());

                    getPresenter().search(searchView.getQuery().toString(), getCollection(), totalItemCount);
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menu.findItem(R.id.action_search_restaurants).getActionView();
        MenuItem menuItem = menu.findItem(R.id.action_search_restaurants);
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
            public boolean onQueryTextChange(String restaurantName) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (!searchView.isIconified()) {
                        getPresenter().search(restaurantName, getCollection(),0);
                    }
                });

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getPresenter().navigateBack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        restaurantsAdapter.unregisterAdapterDataObserver(emptyRecyclerViewObserver);
    }

    @Override
    public void onRestaurantsListLoaded(@NonNull List<Restaurant> restaurants) {
        Log.e(TAG, "Loaded data");
        restaurantsAdapter.setLoaded();
        restaurantsAdapter.swapData(restaurants);
    }

    @Override
    public void onRestaurantsListPageLoaded(@NonNull List<Restaurant> restaurants) {
        Log.e(TAG, "Loaded paged data");
        if (restaurants.size() > 0) {
            restaurantsAdapter.addToBottom(restaurants);
        }
    }

    @Override
    public void showLoadingMoreItems() {
        restaurantsAdapter.setLoading();
    }

    @Override
    public void hideLoadingMoreItems() {
        restaurantsAdapter.setLoaded();
    }

    @Override
    public void setHasMore(boolean hasMore) {
        restaurantsAdapter.setHasMore(hasMore);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_restaurants;
    }

    @Override
    protected int getTitle() {
        return R.string.restaurants_title;
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_restaurants;
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
    public void onRestaurantClick(@NonNull Restaurant restaurant) {
        getPresenter().showRestaurantDetails(restaurant);
    }

    @NonNull
    public Collection getCollection() {
        Collection collection = getArguments().getParcelable(PARAM_COLLECTION);
        Objects.requireNonNull(collection, "Collection can't be null!");

        return collection;
    }
}