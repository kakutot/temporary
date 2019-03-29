package com.example.tupkalenko.trainee.project.exception;

public class NoCityFoundException extends NoSuchEntityException {

    public NoCityFoundException() {
        super("No such city");
    }
}
