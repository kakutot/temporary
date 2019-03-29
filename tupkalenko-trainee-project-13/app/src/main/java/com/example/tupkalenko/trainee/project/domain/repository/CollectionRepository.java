package com.example.tupkalenko.trainee.project.domain.repository;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface CollectionRepository {

    @NonNull
    Single<List<Collection>> getCollectionsByCityId(int cityId);

    @NonNull
    Single<List<Collection>> getCollectionsByCityId(int cityId, int count);
}