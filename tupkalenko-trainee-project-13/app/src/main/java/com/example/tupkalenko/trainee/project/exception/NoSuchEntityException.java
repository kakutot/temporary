package com.example.tupkalenko.trainee.project.exception;

public abstract class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message) {
        super(message);
    }
}
