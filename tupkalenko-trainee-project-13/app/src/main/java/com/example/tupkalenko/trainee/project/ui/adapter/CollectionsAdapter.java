package com.example.tupkalenko.trainee.project.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tupkalenko.trainee.project.R;
import com.example.tupkalenko.trainee.project.domain.entity.Collection;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class CollectionsAdapter extends BaseAdapter<Collection> {

    @NonNull
    private OnCollectionClickListener onCollectionClickListener;

    public CollectionsAdapter(@NonNull OnCollectionClickListener onCollectionClickListener) {
        this.onCollectionClickListener = onCollectionClickListener;
    }

    @Override
    BaseHolder<Collection> onCreateViewHolder(@NonNull View view, int viewType) {
        return new CollectionViewHolder(view);
    }

    @Override
    int getLayoutId(int viewType) {
        return R.layout.item_collection;
    }

    public class CollectionViewHolder extends BaseHolder<Collection> {

        @BindView(R.id.titleTv)
        TextView titleTv;

        @BindView(R.id.photoIv)
        ImageView photoIv;

        @BindView(R.id.descriptionTv)
        TextView descriptionTv;

        @BindView(R.id.resultsCountTv)
        TextView resultsCountTv;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        void bind(@NonNull Collection item) {
            titleTv.setText(item.getTitle());
            //TODO load image
            photoIv.setImageResource(R.drawable.ic_launcher_background);
            descriptionTv.setText(item.getDescription());
            resultsCountTv.setText(String.valueOf(item.getResultsCount()));
        }

        @OnClick(R.id.collectionsContentCv)
        public void onCollectionClick() {
            Collection collection = getData().get(getAdapterPosition());
            onCollectionClickListener.onCollectionClick(collection);
        }
    }

    public interface OnCollectionClickListener {

        void onCollectionClick(@NonNull Collection collection);
    }
}