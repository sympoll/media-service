package com.mtapizza.sympoll.mediaservice.controller;

import com.mtapizza.sympoll.mediaservice.dto.request.image.ImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageRetrieveResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.exception.image.upload.ImageUploadFailedException;
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

    @PostMapping("/upload-profile-picture")
    public ResponseEntity<ImageUploadResponse> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            @RequestPart ImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload profile picture: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadProfilePicture(file, uploadInfo));
    }

    @PostMapping("/upload-profile-banner")
    public ResponseEntity<ImageUploadResponse> uploadProfileBanner(
            @RequestParam("file") MultipartFile file,
            @RequestPart ImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload profile banner: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadProfileBanner(file, uploadInfo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws ImageNotFoundException, ImageIOException, ImageDataFormatException {
        log.info("Received request to retrieve image with id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "image/png")
                .body(imageService.getImage(id));
    }
}
