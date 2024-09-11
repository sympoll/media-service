package com.mtapizza.sympoll.mediaservice.service.image;

import com.mtapizza.sympoll.mediaservice.client.GroupClient;
import com.mtapizza.sympoll.mediaservice.client.UserClient;
import com.mtapizza.sympoll.mediaservice.dto.request.group.GroupUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.GroupUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.image.GroupImageDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.image.GroupImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.image.UserImageDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.image.UserImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.UserUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.UserUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageDeleteResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.exception.image.upload.ImageUploadFailedException;
import com.mtapizza.sympoll.mediaservice.model.image.Image;
import com.mtapizza.sympoll.mediaservice.repository.image.ImageRepository;
import com.mtapizza.sympoll.mediaservice.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserClient userClient;
    private final GroupClient groupClient;

    @Value("${media.service.url}")
    private String mediaServiceUrl;

    /**
     * Upload a user profile picture.
     * Sends a request to the user-service to save the newly added user profile picture's url.
     * @param file File of the profile picture to upload.
     * @param uploadInfo Info on the upload / uploader.
     * @return Information on the uploaded picture.
     */
    @Transactional
    public ImageUploadResponse uploadUserProfilePicture(MultipartFile file, UserImageUploadRequest uploadInfo)
            throws ImageIOException, ImageUploadFailedException {
        log.info("Uploading user profile picture.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.ownerUserId().toString());

        // Update the profile picture of the user
        log.info("Sending request to user-service to update the user's profile picture url.");
        UUID updatedUserId = userClient.userUpdateProfilePictureUrl(
                new UserUpdateProfilePictureUrlRequest(
                        uploadInfo.ownerUserId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if(!updatedUserId.equals(uploadInfo.ownerUserId())) {
            throw new ImageUploadFailedException("Owner user id mismatch: " + updatedUserId + "(saved user ID), " + uploadInfo.ownerUserId() + "(requested user ID)");
        }

        return uploadedImageResponse;
    }

    /**
     * Upload a user profile banner.
     * Sends a request to the user-service to save the newly added user banner picture's url.
     * @param file File of the banner picture to upload.
     * @param uploadInfo Info on the upload / uploader.
     * @return Information on the uploaded picture.
     */
    @Transactional
    public ImageUploadResponse uploadUserProfileBanner(MultipartFile file, UserImageUploadRequest uploadInfo)
            throws ImageIOException, ImageUploadFailedException {
        log.info("Uploading user profile banner.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.ownerUserId().toString());

        // Update the banner picture of the user
        log.info("Sending request to group-service to update the user's banner picture url.");
        UUID updatedUserId = userClient.userUpdateBannerPictureUrl(
                new UserUpdateBannerPictureUrlRequest(
                        uploadInfo.ownerUserId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if(!updatedUserId.equals(uploadInfo.ownerUserId())) {
            throw new ImageUploadFailedException("Owner user id mismatch: " + updatedUserId + "(saved user ID), " + uploadInfo.ownerUserId() + "(requested user ID)");
        }

        return uploadedImageResponse;
    }

    /**
     * Upload a group profile picture.
     * Sends a request to the user-service to save the newly added group profile picture's url.
     * @param file File of the profile picture to upload.
     * @param uploadInfo Info on the upload / uploader.
     * @return Information on the uploaded picture.
     */
    @Transactional
    public ImageUploadResponse uploadGroupProfilePicture(MultipartFile file, GroupImageUploadRequest uploadInfo)
            throws ImageIOException, ImageUploadFailedException {
        log.info("Uploading group profile picture.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.groupId());

        // Update the profile picture of the user
        log.info("Sending request to group-service to update the group's profile picture url.");
        String updatedGroupId = groupClient.groupUpdateProfilePictureUrl(
                new GroupUpdateProfilePictureUrlRequest(
                        uploadInfo.groupId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if(!updatedGroupId.equals(uploadInfo.groupId())) {
            throw new ImageUploadFailedException("Group id mismatch: " + updatedGroupId + "(saved group ID), " + uploadInfo.groupId() + "(requested group ID)");
        }

        return uploadedImageResponse;
    }

    /**
     * Upload a group profile banner.
     * Sends a request to the user-service to save the newly added group banner picture's url.
     * @param file File of the banner picture to upload.
     * @param uploadInfo Info on the upload / uploader.
     * @return Information on the uploaded picture.
     */
    @Transactional
    public ImageUploadResponse uploadGroupProfileBanner(MultipartFile file, GroupImageUploadRequest uploadInfo)
            throws ImageIOException, ImageUploadFailedException {
        log.info("Uploading group profile banner.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.groupId());

        // Update the banner picture of the user
        log.info("Sending request to user-service to update the group's banner picture url.");
        String updatedGroupId = groupClient.groupUpdateBannerPictureUrl(
                new GroupUpdateBannerPictureUrlRequest(
                        uploadInfo.groupId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if(!updatedGroupId.equals(uploadInfo.groupId())) {
            throw new ImageUploadFailedException("Group id mismatch: " + updatedGroupId + "(saved group ID), " + uploadInfo.groupId() + "(requested group ID)");
        }

        return uploadedImageResponse;
    }

    /**
     * Saves the image to the media-db, after compressing it.
     * @param file Image file to save.
     * @return Information on the uploaded picture.
     */
    private ImageUploadResponse saveImage(MultipartFile file, String ownerId) throws ImageIOException {
        try {
            log.info("Saving image in the database.");
            Image imageToSave = Image.builder()
                    .ownerId(ownerId)
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtils.compressImage(file.getBytes()))
                    .build();

            imageRepository.save(imageToSave);
            log.info("Successfully saved image into the database.");

            String imageUrl = mediaServiceUrl + imageToSave.getId();
            return new ImageUploadResponse(
                    imageToSave.getOwnerId(),
                    imageUrl,
                    imageToSave.getName(),
                    imageToSave.getType(),
                    imageToSave.getTimeUploaded()
            );
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new ImageIOException("Failed to upload image: " + ex);
        }
    }

    /**
     * Download an image from the media-db, while decompressing it.
     * @return The image file.
     */
    public byte[] getImage(Long id) throws ImageNotFoundException, ImageIOException, ImageDataFormatException {
        Image retrievedImg = imageRepository
                .findById(id)
                .orElseThrow(
                        () -> new ImageNotFoundException(id)
                );
        try {
            return ImageUtils.decompressImage(retrievedImg.getData());
        } catch (IOException ex) {
            throw new ImageIOException("Failed to download image: " + ex);
        } catch (DataFormatException ex) {
            throw new ImageDataFormatException("Failed to download image: " + ex);
        }
    }

    /**
     * Delete a user image from the media DB.
     * @param userImageDeleteRequest Information on the user's image to delete.
     * @return Information on the deletion that was completed.
     */
    @Transactional
    public ImageDeleteResponse deleteUserImage(UserImageDeleteRequest userImageDeleteRequest) {
        // TODO: validate the request

        return deleteImage(userImageDeleteRequest.imageUrl());
    }


    /**
     * Delete a group image from the media DB.
     * @param groupImageDeleteRequest Information on the group's image to delete.
     * @return Information on the deletion that was completed.
     */
    @Transactional
    public ImageDeleteResponse deleteGroupImage(GroupImageDeleteRequest groupImageDeleteRequest) {
        // TODO: validate the request

        return deleteImage(groupImageDeleteRequest.imageUrl());
    }

    /**
     * Delete an image from the media DB.
     * @param imageUrl URL of the image to delete.
     */
    private ImageDeleteResponse deleteImage(String imageUrl) {
        Long imageToDeleteId = Long.valueOf(imageUrl.replaceFirst(mediaServiceUrl, ""));

        log.info("Deleting image with id: {}", imageToDeleteId);
        imageRepository.deleteById(imageToDeleteId);
        log.info("Successfully deleted image with id: {}", imageToDeleteId);

        return new ImageDeleteResponse(imageUrl);
    }
}
