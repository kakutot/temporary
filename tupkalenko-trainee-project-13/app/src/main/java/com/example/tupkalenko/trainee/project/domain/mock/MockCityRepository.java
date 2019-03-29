package com.example.tupkalenko.trainee.project.domain.mock;

import com.example.tupkalenko.trainee.project.domain.entity.City;
import com.example.tupkalenko.trainee.project.exception.NoCityFoundException;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import io.reactivex.Single;

public class MockCityRepository implements CityRepository {

    @NonNull
    private final Map<Pair<String, Pair<Double, Double>>, City> cities;

    public MockCityRepository() {
        cities = new HashMap<>();
        initMockCities();
    }

    private void initMockCities() {
        for(int i = 0;i < 5;i++) {
            putMockCity(i + 1,
                        "test" + (i + 1),
                        "tesCountryName" + (i + 1),
                        i + 1);
            putMockCityWithCoordinates(i + 1,
                                       "test" + (i + 1),
                                       "tesCountryName" + (i + 1),
                                       i + 1,
                                       i + 1,
                                       i + 1);
        }
    }

    private void putMockCity(int id,
                             @NonNull String cityName,
                             @NonNull String countryName,
                             int countryId) {
        City city = new City();
        city.setId(id);
        city.setName(cityName);
        city.setCountryName(countryName);
        city.setCountryId(countryId);

        cities.put(Pair.create(cityName, null), city);
    }

    private void putMockCityWithCoordinates(int id,
                                            @NonNull String cityName,
                                            @NonNull String countryName,
                                            int countryId,
                                            double lat,
                                            double lon) {
        City city = new City();
        city.setId(id);
        city.setName(cityName);
        city.setCountryName(countryName);
        city.setCountryId(countryId);

        cities.put(Pair.create(null, Pair.create(lat, lon)), city);
    }

    @NonNull
    @Override
    public Single<City> getCity(@NonNull String cityName) {
        return Single.fromCallable(() -> cityByName(cityName));
    }

    @NonNull
    @Override
    public Single<City> getCityByCoordinates(double lat, double lng) {
        return Single.fromCallable(() -> cityByCoordinates(lat, lng));
    }

    @NonNull
    private City cityByName(@NonNull String cityName) {
       City city = null;

       for (Pair<String, Pair<Double, Double>> key : cities.keySet()) {
           if (key.first != null && key.first.equalsIgnoreCase(cityName)) {
               city = cities.get(key);
           }
       }
       if (city == null) {
           throw new NoCityFoundException();
       }

       return city;
    }

    @NonNull
    private City cityByCoordinates(double lat, double lng) {
        City city = null;
        
        for (Pair<String, Pair<Double, Double>> key : cities.keySet()) {
            if (key.second != null &&
                key.second.first != null && key.second.first == lat &&
                key.second.second != null && key.second.second == lng) {

                city = cities.get(key);
            }
        }
        if (city == null) {
            throw new NoCityFoundException();
        }

        return city;
    }
}