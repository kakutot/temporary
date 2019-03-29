package com.example.tupkalenko.trainee.project.domain.mock;

import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;
import com.example.tupkalenko.trainee.project.exception.NoCollectionsFoundException;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import io.reactivex.Single;

public class MockCollectionRepository implements CollectionRepository {

    @NonNull
    private Map<Pair<Integer, String>, Collection> collections;

    public MockCollectionRepository() {
        collections = new HashMap<>();
        initMockCollections();
    }

    private void initMockCollections() {
        for(int i = 0;i < 5;i++) {
            putMockCollection(i + 1,
                              "title" + (i + 1),
                              "Description" + (i + 1),
                              "url" + (i + 1),
                              (i + 1) * 3,
                              1);
        }
    }

    private void putMockCollection(int collectionId,
                                   @NonNull String title,
                                   @NonNull String description,
                                   @NonNull String imageUrl,
                                   int resultsCount,
                                   int cityId) {
        Collection collection = new Collection();
        collection.setId(collectionId);
        collection.setTitle(title);
        collection.setImageUrl(imageUrl);
        collection.setDescription(description);
        collection.setResultsCount(resultsCount);
        String suffix = generateRandomSuffix();

        collections.put(Pair.create(cityId, suffix), collection);
    }

    private String generateRandomSuffix() {
        byte[] array = new byte[1];
        new Random().nextBytes(array);

        return new String(array, Charset.forName("UTF-8"));
    }

    @NonNull
    public Single<List<Collection>> getCollectionsByCityId(int cityId) {
        return Single.fromCallable(() -> {
            List<Collection> result = new LinkedList<>();

            for(Pair<Integer, String> key : collections.keySet()) {
                if (key.first != null && key.first == cityId) {
                    result.add(collections.get(key));
                }
            }
            if (result.isEmpty()) {
                throw new NoCollectionsFoundException();
            }

            return result;
        });
    }

    @NonNull
    public Single<List<Collection>> getCollectionsByCityId(int cityId, int count) {
        return Single.fromCallable(() -> {
            List<Collection> result = new LinkedList<>();
            int counter = 0;

            for(Map.Entry<Pair<Integer, String>, Collection> entry : collections.entrySet()) {
                if (entry.getKey().first == cityId && counter++ < count) {
                    result.add(entry.getValue());
                }
            }
            if (result.isEmpty()) {
                throw new NoCollectionsFoundException();
            }

            return result;
        });
    }
}