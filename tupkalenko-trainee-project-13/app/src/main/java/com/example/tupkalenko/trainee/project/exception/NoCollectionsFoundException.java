package com.example.tupkalenko.trainee.project.exception;

public class NoCollectionsFoundException extends NoSuchEntityException {

    public NoCollectionsFoundException() {
        super("No collections found");
    }
}
