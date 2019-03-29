package com.example.tupkalenko.trainee.project.exception;

public class NoRestaurantFoundException extends NoSuchEntityException {

    public NoRestaurantFoundException() {
        super("No such restaurant");
    }
}