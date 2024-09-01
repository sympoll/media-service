package com.mtapizza.sympoll.mediaservice.exception.image.upload;

public class ImageUploadFailedException extends RuntimeException {
    public ImageUploadFailedException(String message) {
        super("Image upload failed: " + message);
    }
}
