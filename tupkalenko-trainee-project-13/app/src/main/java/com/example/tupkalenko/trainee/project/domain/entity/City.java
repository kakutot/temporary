package com.example.tupkalenko.trainee.project.domain.entity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class City {

    private int id;

    @Nullable
    private String name;

    private int countryId;

    @Nullable
    private String countryName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Nullable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@NonNull String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City)o;

        return id == city.id &&
               countryId == city.countryId &&
               Objects.equals(name, city.name) &&
               Objects.equals(countryName, city.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryId, countryName);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}