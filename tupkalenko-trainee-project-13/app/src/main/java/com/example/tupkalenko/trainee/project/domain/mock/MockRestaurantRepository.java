package com.example.tupkalenko.trainee.project.domain.mock;

import android.util.Log;

import com.example.tupkalenko.trainee.project.domain.entity.Location;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.domain.entity.UserRating;
import com.example.tupkalenko.trainee.project.exception.NoRestaurantFoundException;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import io.reactivex.Single;

public class MockRestaurantRepository implements RestaurantRepository {

    private final static int DEFAULT_ITEMS_COUNT = 5;

    @NonNull
    private final Map<Pair<Integer, Pair<String, Integer>>, Restaurant> restaurants;
    private List<Location> locations;

    public MockRestaurantRepository() {
        this.restaurants = new HashMap<>();
        initLocations();
        initMockRestaurants();
    }
    private void initLocations() {
        locations = new LinkedList<>();

        Location location = new Location();
        location.setCity("Milan");
        location.setAddress("Via Varesina, 61");
        locations.add(location);

        location = new Location();
        location.setCity("Paris");
        location.setAddress("Some street, 31");
        locations.add(location);

        location = new Location();
        location.setCity("Munich");
        location.setAddress("Some street, 99");
        locations.add(location);

        location = new Location();
        location.setCity("Oslo");
        location.setAddress("Some street, 73");
        locations.add(location);

        location = new Location();
        location.setCity("Kharkiv");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("Lissabon");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("Kiev2");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("A");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("B");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("C");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("TD");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("E");
        location.setAddress("Some street, 12");
        locations.add(location);

        location = new Location();
        location.setCity("N");
        location.setAddress("Some street, 12");
        locations.add(location);
    }

    private void initMockRestaurants() {
        for(int i = 0; i < 13; i++) {
            putMockRestaurantWithCollectionId(i + 1,
                                              5,
                                              "test" + i,
                                               locations.get(i),
                                              "$",
                                               generateRandomString(i * 4),
                                               new UserRating(),
                                               (float)i,
                                               i % 2 == 0,
                                               i % 2 != 0);
        }
    }

    private void putMockRestaurant(int restaurantId,
                                   @NonNull String restaurantName,
                                   @Nullable Location location,
                                   @Nullable String currency,
                                   @Nullable String featuredImage,
                                   @Nullable UserRating userRating,
                                   float averageCostForTwo,
                                   boolean isDeliveringNow,
                                   boolean hasTableBooking) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName(restaurantName);
        restaurant.setLocation(location);
        restaurant.setAverageCostForTwo(averageCostForTwo);
        restaurant.setCurrency(currency);
        restaurant.setFeaturedImage(featuredImage);
        restaurant.setUserRating(userRating);
        restaurant.setDeliveringNow(isDeliveringNow);
        restaurant.setHasTableBooking(hasTableBooking);

        restaurants.put(Pair.create(restaurantId, null), restaurant);
    }

    private void putMockRestaurantWithCollectionId(int restaurantId,
                                                   int collectionId,
                                                   @NonNull String restaurantName,
                                                   @Nullable Location location,
                                                   @Nullable String currency,
                                                   @Nullable String featuredImage,
                                                   @Nullable UserRating userRating,
                                                   float averageCostForTwo,
                                                   boolean isDeliveringNow,
                                                   boolean hasTableBooking) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName(restaurantName);
        restaurant.setLocation(location);
        restaurant.setAverageCostForTwo(averageCostForTwo);
        restaurant.setCurrency(currency);
        restaurant.setFeaturedImage(featuredImage);
        restaurant.setUserRating(userRating);
        restaurant.setDeliveringNow(isDeliveringNow);
        restaurant.setHasTableBooking(hasTableBooking);

        restaurants.put(Pair.create(null, Pair.create(restaurantName, collectionId)), restaurant);
    }

    private String generateRandomString(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);

        return new String(array, Charset.forName("UTF-8"));
    }

    @NonNull
    @Override
    public Single<List<Restaurant>> getRestaurantsByCollectionId(@NonNull String restaurantName,
                                                                 int collectionId,
                                                                 int start) {
        return Single.fromCallable(() -> getRestaurants(restaurantName,
                                                        collectionId,
                                                        start,
                                                        DEFAULT_ITEMS_COUNT));
    }


    @NonNull
    @Override
    public Single<Restaurant> getRestaurant(int restaurantId) {
        return Single.fromCallable(() -> getRestaurantById(restaurantId));
    }

    @NonNull
    private Restaurant getRestaurantById(int restaurantId) {
        for(Map.Entry<Pair<Integer, Pair<String, Integer>>, Restaurant> entry :
            restaurants.entrySet()) {
            if (entry.getKey().first == restaurantId) {
                return entry.getValue();
            }
        }

        throw new NoRestaurantFoundException();
    }

    @NonNull
    private List<Restaurant> getRestaurants(@NonNull String restaurantName,
                                            int collectionId,
                                            int start,
                                            int count) {
        List<Restaurant> result = new LinkedList<>();
        int counter = 0;
        int curPos = 0;
        if (restaurantName.isEmpty()) {
            return getRestaurantsForEmptyName(collectionId, start, count);
        }
        for(Map.Entry<Pair<Integer, Pair<String, Integer>>, Restaurant> entry :
            restaurants.entrySet()) {
            if (start > curPos++) {
                continue;
            }
            if (entry.getKey().second != null &&
                entry.getKey().second.first != null &&
                entry.getKey().second.first.equals(restaurantName) &&
                entry.getKey().second.second != null &&
                entry.getKey().second.second == collectionId &&
                counter++ < count) {
                result.add(entry.getValue());
            }
        }

        return result;
    }

    private List<Restaurant> getRestaurantsForEmptyName(int collectionId, int start, int count) {
        List<Restaurant> result = new LinkedList<>();
        int counter = 0;
        int curPos = 0;

        if (start >= restaurants.entrySet().size()) {
            return result;
        }
        for(Map.Entry<Pair<Integer, Pair<String, Integer>>, Restaurant> entry :
                restaurants.entrySet()) {
            if (counter == count) {
                break;
            }
            if (start > curPos++) {
                continue;
            }
            if (entry.getKey().second != null &&
                entry.getKey().second.second != null &&
                entry.getKey().second.second == collectionId &&
                counter++ < count) {
                result.add(entry.getValue());
            }
        }

        return result;
    }
}