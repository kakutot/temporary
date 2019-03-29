package com.example.tupkalenko.trainee.project.ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.domain.entity.Location;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.ui.paging.DataLoadingState;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;

import static com.example.tupkalenko.trainee.project.ui.paging.DataLoadingState.LOADED;
import static com.example.tupkalenko.trainee.project.ui.paging.DataLoadingState.LOADING;

public class RestaurantsAdapter extends BaseAdapter<Restaurant> {

    private final static int DEFAULT_VIEW_TYPE = 1;
    private final static int LOADING_VIEW_TYPE = 0;

    @Nullable
    private boolean isLoading = false;
    private boolean hasMore = true;

    @NonNull
    private OnRestaurantClickListener onRestaurantClickListener;

    public RestaurantsAdapter(@NonNull OnRestaurantClickListener onRestaurantClickListener) {
        this.onRestaurantClickListener = onRestaurantClickListener;
    }

    @Override
    BaseHolder<Restaurant> onCreateViewHolder(@NonNull View view, int viewType) {
        BaseHolder<Restaurant> baseHolder = null;

        if (viewType == DEFAULT_VIEW_TYPE) {
            baseHolder = new RestaurantViewHolder(view);
        }
        if (viewType == LOADING_VIEW_TYPE) {
            baseHolder = new RestaurantLoadingViewHolder(view);
        }

        Objects.requireNonNull(baseHolder, "Holder can't be null!");
        return baseHolder;
    }

    public void addToBottom(List<Restaurant> items) {
        getData().addAll(items);
        notifyDataSetChanged();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setLoading() {
        this.isLoading = true;
        notifyItemInserted(getData().size());
    }

    public void setLoaded() {
        isLoading = false;
        //getData().remove(getData().size()-1);
        notifyItemRemoved(getData().size());
    }

    @Override
    public int getItemCount() {
        return  isLoading && hasMore ? getData().size()+1 : getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return LOADING_VIEW_TYPE;
        } else {
            return DEFAULT_VIEW_TYPE;
        }
    }

    @Override
    int getLayoutId(int viewType) {
        if (viewType == LOADING_VIEW_TYPE) {
            return R.layout.item_restaurant_loading;
        } else if (viewType == DEFAULT_VIEW_TYPE) {
            return R.layout.item_restaurant;
        }

        return -1;
    }

    public class RestaurantViewHolder extends BaseHolder<Restaurant> {
        private View itemView;

        @BindView(R.id.restaurantNameTv)
        TextView restaurantNameTv;

        @BindView(R.id.cityTv)
        TextView cityTv;

        @BindView(R.id.addressTv)
        TextView addressTv;

        @BindView(R.id.hasOnlineDeliveryView)
        View hasOnlineDeliveryView;

        @BindView(R.id.hasTableBookingview)
        View hasTableBookingView;

        @BindView(R.id.restaurantPhotoIv)
        ImageView restaurantPhotoIv;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        @Override
        void bind(@NonNull Restaurant item) {
            restaurantNameTv.setText(item.getName());
            //TODO load image
            restaurantPhotoIv.setImageResource(R.drawable.ic_launcher_background);
            String city = item.getLocation().getCity();
            if (city != null) {
                cityTv.setText(city);
            }
            String address = item.getLocation().getAddress();
            if (address != null) {
                addressTv.setText(address);
            }
            GradientDrawable background = (GradientDrawable) hasOnlineDeliveryView.getBackground();
            setBackgroundColor(item.isDeliveringNow(), background, itemView.getContext());

            background = (GradientDrawable) hasTableBookingView.getBackground();
            setBackgroundColor(item.hasTableBooking(), background, itemView.getContext());
        }
    }

    public class RestaurantLoadingViewHolder extends BaseHolder<Restaurant> {
        @BindView(R.id.restaurantItemPB)
        ProgressBar progressBar;

        public RestaurantLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(@NonNull Restaurant item) {
            // none
        }
    }

    private void setBackgroundColor(boolean condition, @NonNull GradientDrawable background, @NonNull Context context) {
        if (condition) {
            background.setColor(ContextCompat.getColor(context, R.color.colorYes));
        } else {
            background.setColor(ContextCompat.getColor(context, R.color.colorNo));
        }
    }

    public interface OnRestaurantClickListener {

        void onRestaurantClick(@NonNull Restaurant restaurant);
    }
}
