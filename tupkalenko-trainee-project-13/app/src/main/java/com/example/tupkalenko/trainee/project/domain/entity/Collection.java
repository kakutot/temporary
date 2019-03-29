package com.example.tupkalenko.trainee.project.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Collection implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    private int id;

    @Nullable
    private String title;

    @Nullable
    private String imageUrl;

    @Nullable
    private String description;

    private int resultsCount;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public Collection() {

    }

    public Collection(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
        this.resultsCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
        dest.writeInt(this.resultsCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection)o;

        return id == that.id &&
                resultsCount == that.resultsCount &&
                Objects.equals(title, that.title) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(description, that.description) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, imageUrl, description, resultsCount);
    }

    @Override
    public String toString() {
        return "Collection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", resultsCount=" + resultsCount +
                '}';
    }
}