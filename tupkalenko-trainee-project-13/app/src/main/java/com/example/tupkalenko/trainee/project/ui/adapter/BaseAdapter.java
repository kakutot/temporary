package com.example.tupkalenko.trainee.project.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    @NonNull
    private final List<T> data = new ArrayList<>();

    abstract BaseHolder<T> onCreateViewHolder(@NonNull View view, int viewType);

    @LayoutRes
    abstract int getLayoutId(int viewType);

    @NonNull
    @Override
    public BaseHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onCreateViewHolder(inflateView(parent,viewType), viewType);
    }

    public void swapData(@NonNull List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    private View inflateView(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(getLayoutId(viewType), parent, false);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        Log.i(this.getClass().getName(),"onBIndViewHolder , current viewtype :" + getItemViewType(position));
        if (this instanceof RestaurantsAdapter) {
            Log.i(this.getClass().getName(),"onBIndViewHolder , isLoading :" + ((RestaurantsAdapter) this).isLoading());
        }
        T item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @NonNull
    public List<T> getData() {
        return data;
    }
}