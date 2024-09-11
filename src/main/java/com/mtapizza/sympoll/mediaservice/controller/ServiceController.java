package com.mtapizza.sympoll.mediaservice.controller;

import com.mtapizza.sympoll.mediaservice.dto.request.group.image.GroupImageDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.image.GroupImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.image.UserImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageDeleteResponse;
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

    @PostMapping("/user/upload-profile-picture")
    public ResponseEntity<ImageUploadResponse> uploadUserProfilePicture(
            @RequestParam("file") MultipartFile file,
            @RequestPart UserImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload a user profile picture: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadUserProfilePicture(file, uploadInfo));
    }

    @PostMapping("/user/upload-profile-banner")
    public ResponseEntity<ImageUploadResponse> uploadUserProfileBanner(
            @RequestParam("file") MultipartFile file,
            @RequestPart UserImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload a user profile banner: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadUserProfileBanner(file, uploadInfo));
    }

    @PostMapping("/group/upload-profile-picture")
    public ResponseEntity<ImageUploadResponse> uploadGroupProfilePicture(
            @RequestParam("file") MultipartFile file,
            @RequestPart GroupImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload a group profile picture: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadGroupProfilePicture(file, uploadInfo));
    }

    @PostMapping("/group/upload-profile-banner")
    public ResponseEntity<ImageUploadResponse> uploadGroupProfileBanner(
            @RequestParam("file") MultipartFile file,
            @RequestPart GroupImageUploadRequest uploadInfo
    ) throws ImageIOException, ImageUploadFailedException {
        log.info("Received request to upload a group profile banner: {}", uploadInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imageService.uploadGroupProfileBanner(file, uploadInfo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable Long id
    ) throws ImageNotFoundException, ImageIOException, ImageDataFormatException {
        log.info("Received request to retrieve image with id: {}", id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "image/png")
                .body(imageService.getImage(id));
    }


    @DeleteMapping("/group")
    public ResponseEntity<ImageDeleteResponse> deleteGroupImage(
            @RequestBody GroupImageDeleteRequest groupImageDeleteRequest
    ) {
        log.info("Received request to delete image for group with ID: {}", groupImageDeleteRequest.groupId());
        log.debug("Received URL of image to delete: {}", groupImageDeleteRequest.imageUrl());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(imageService.deleteGroupImage(groupImageDeleteRequest));
    }
}
