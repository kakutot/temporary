package com.example.tupkalenko.trainee.project.ui;

import android.view.View;
import android.widget.TextView;

import com.example.tupkalenko.trainee.project.ui.adapter.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecyclerViewObserver<T> extends RecyclerView.AdapterDataObserver {

    @NonNull
    private final BaseAdapter<T> adapter;

    @NonNull
    private TextView emptyTextView;

    @Nullable
    private String currentText;

    public EmptyRecyclerViewObserver(@NonNull TextView emptyTextView,
                                     @NonNull BaseAdapter<T> adapter) {
        this.emptyTextView = emptyTextView;
        this.adapter = adapter;
    }

    public void setCurrentText(@NonNull String currentText) {
        this.currentText = currentText;
    }

    @Override
    public void onChanged() {
        if (currentText == null) {
            emptyTextView.setVisibility(View.GONE);
            return;
        }
        if (adapter.getData().size() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(currentText);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }
}