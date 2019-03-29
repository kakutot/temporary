package com.example.tupkalenko.trainee.project.domain.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class UserRating {

    private float aggregateRating;

    @Nullable
    private String ratingText;

    @Nullable
    private String ratingColor;

    private int votes;

    public float getAggregateRating() {
        return aggregateRating;
    }

    public void setAggregateRating(float aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    @Nullable
    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(@NonNull String ratingText) {
        this.ratingText = ratingText;
    }

    @Nullable
    public String getRatingColor() {
        return ratingColor;
    }

    public void setRatingColor(@NonNull String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}