package com.mtapizza.sympoll.mediaservice.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(Long id) {
        super("Image with id " + id + " not found");
    }
}
