package com.mtapizza.sympoll.mediaservice.controller;

import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageRetrieveResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class ServiceController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("file") MultipartFile file) throws ImageIOException {
        log.info("Received request to upload image");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.saveImage(file));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageRetrieveResponse> getImage(@PathVariable Long id) throws ImageNotFoundException, ImageIOException, ImageDataFormatException {
        log.info("Received request to retrieve image with id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageService.getImage(id));
    }
}
