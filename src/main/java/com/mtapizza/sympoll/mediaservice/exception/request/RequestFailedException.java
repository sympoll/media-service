package com.mtapizza.sympoll.mediaservice.exception.request;

public class RequestFailedException extends RuntimeException {
    public RequestFailedException(String message) {
        super(message);
    }
}
