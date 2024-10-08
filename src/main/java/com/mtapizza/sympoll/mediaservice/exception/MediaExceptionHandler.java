package com.mtapizza.sympoll.mediaservice.exception;

import com.mtapizza.sympoll.mediaservice.dto.response.error.GeneralMediaErrorResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.error.image.data.format.ImageDataFormatExceptionErrorResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.error.image.io.exception.ImageIOExceptionErrorResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.error.image.not.found.ImageNotFoundErrorResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.error.image.upload.ImageUploadFailedErrorResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.exception.image.upload.ImageUploadFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * A global exception handler for the entire Media-Service
 */
@ControllerAdvice
@Slf4j
public class MediaExceptionHandler {
    /**
     * Handles image IO exceptions.
     */
    @ExceptionHandler(ImageIOException.class)
    public ResponseEntity<ImageIOExceptionErrorResponse> handleImageIOException(Exception ex, WebRequest request) {
        log.info("Encountered an image IO exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ImageIOExceptionErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles image upload failed exceptions.
     */
    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ImageUploadFailedErrorResponse> handleImageUploadFailedException(Exception ex, WebRequest request) {
        log.info("Encountered an image upload failed exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ImageUploadFailedErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles image data format exceptions.
     */
    @ExceptionHandler(ImageDataFormatException.class)
    public ResponseEntity<ImageDataFormatExceptionErrorResponse> handleImageDataFormatException(Exception ex, WebRequest request) {
        log.info("Encountered an image data format exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ImageDataFormatExceptionErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles image not found exceptions.
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ImageNotFoundErrorResponse> handleImageNotFoundException(Exception ex, WebRequest request) {
        log.info("Encountered an image not found exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ImageNotFoundErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralMediaErrorResponse> handleGeneralException(Exception ex, WebRequest request) {
        log.info("Encountered a general exception: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralMediaErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
