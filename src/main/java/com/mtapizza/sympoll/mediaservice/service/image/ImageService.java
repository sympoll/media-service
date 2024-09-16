package com.mtapizza.sympoll.mediaservice.service.image;

import com.mtapizza.sympoll.mediaservice.client.GroupClient;
import com.mtapizza.sympoll.mediaservice.client.UserClient;
import com.mtapizza.sympoll.mediaservice.dto.request.group.delete.GroupDataDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateBannerPictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.delete.GroupImageDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.group.upload.GroupImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.delete.UserDataDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.delete.UserImageDeleteRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserImageUploadRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateProfileBannerUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.request.user.upload.UserUpdateProfilePictureUrlRequest;
import com.mtapizza.sympoll.mediaservice.dto.response.group.delete.GroupDataDeleteResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageDeleteResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.user.delete.UserDataDeleteResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.user.update.UserUpdateProfileBannerUrlResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.user.update.UserUpdateProfilePictureUrlResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.exception.image.upload.ImageUploadFailedException;
import com.mtapizza.sympoll.mediaservice.exception.request.RequestFailedException;
import com.mtapizza.sympoll.mediaservice.model.image.Image;
import com.mtapizza.sympoll.mediaservice.model.owner.type.OwnerType;
import com.mtapizza.sympoll.mediaservice.repository.image.ImageRepository;
import com.mtapizza.sympoll.mediaservice.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        // Save the user profile picture
        log.info("Uploading user profile picture.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.ownerUserId().toString(), OwnerType.USER);

        // Update the profile picture of the user
        log.info("Sending request to user-service to update the user's profile picture url.");
        ResponseEntity<UserUpdateProfilePictureUrlResponse> responseEntity = userClient.userUpdateProfilePictureUrl(
                new UserUpdateProfilePictureUrlRequest(
                        uploadInfo.ownerUserId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            UserUpdateProfilePictureUrlResponse userUpdateProfilePictureUrlResponse = responseEntity.getBody();

            // Delete the old user profile picture
            assert userUpdateProfilePictureUrlResponse != null;
            if(userUpdateProfilePictureUrlResponse.profilePictureUrl() != null)
                deleteImage(userUpdateProfilePictureUrlResponse.profilePictureUrl());

            if(!userUpdateProfilePictureUrlResponse.userId().equals(uploadInfo.ownerUserId())) {
                throw new ImageUploadFailedException("Owner user id mismatch: " + userUpdateProfilePictureUrlResponse.userId() + "(saved user ID), " + uploadInfo.ownerUserId() + "(requested user ID)");
            }

            return uploadedImageResponse;
        } else {
            String errorMessage = responseEntity.hasBody() ? String.valueOf(responseEntity.getBody()) : "No error message in the response body";
            log.error("Request to update user profile picture via user service failed. Status code {}", responseEntity.getStatusCode());
            throw new RequestFailedException("Request to user service failed. Status code " + responseEntity.getStatusCode() + "error message " + errorMessage);
        }
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
        // Save the user profile picture
        log.info("Uploading user profile banner.");
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.ownerUserId().toString(), OwnerType.USER);

        // Update the profile picture of the user
        log.info("Sending request to user-service to update the user's profile banner url.");
        ResponseEntity<UserUpdateProfileBannerUrlResponse> responseEntity = userClient.userUpdateBannerPictureUrl(
                new UserUpdateProfileBannerUrlRequest(
                        uploadInfo.ownerUserId(),
                        uploadedImageResponse.imageUrl()
                )
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            UserUpdateProfileBannerUrlResponse userUpdateProfileBannerUrlResponse = responseEntity.getBody();

            // Delete the old user profile picture
            assert userUpdateProfileBannerUrlResponse != null;
            if(userUpdateProfileBannerUrlResponse.profileBannerUrl() != null)
                deleteImage(userUpdateProfileBannerUrlResponse.profileBannerUrl());

            if(!userUpdateProfileBannerUrlResponse.userId().equals(uploadInfo.ownerUserId())) {
                throw new ImageUploadFailedException("Owner user id mismatch: " + userUpdateProfileBannerUrlResponse.userId() + "(saved user ID), " + uploadInfo.ownerUserId() + "(requested user ID)");
            }

            return uploadedImageResponse;
        } else {
            String errorMessage = responseEntity.hasBody() ? String.valueOf(responseEntity.getBody()) : "No error message in the response body";
            log.error("Request to update user profile banner via user service failed. Status code {}", responseEntity.getStatusCode());
            throw new RequestFailedException("Request to user service failed. Status code " + responseEntity.getStatusCode() + "error message " + errorMessage);
        }
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
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.groupId(), OwnerType.GROUP);

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
        ImageUploadResponse uploadedImageResponse = saveImage(file, uploadInfo.groupId(), OwnerType.GROUP);

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
    private ImageUploadResponse saveImage(MultipartFile file, String ownerId, OwnerType ownerType) throws ImageIOException {
        try {
            log.info("Saving image in the database.");
            Image imageToSave = Image.builder()
                    .ownerId(ownerId)
                    .ownerType(ownerType)
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

    /**
     * Completely remove media data for a user.
     * @param userDataDeleteRequest Information on the user to delete.
     * @return Information on the user that was deleted.
     */
    @Transactional
    public UserDataDeleteResponse deleteUserData(UserDataDeleteRequest userDataDeleteRequest) {
        // TODO: validate request
        log.info("Deleting user data for user with ID {}.", userDataDeleteRequest.userId());
        imageRepository.deleteByOwnerId(userDataDeleteRequest.userId().toString());

        return new UserDataDeleteResponse(userDataDeleteRequest.userId());
    }

    /**
     * Completely remove media data for a group.
     * @param groupDataDeleteRequest Information on the group to delete.
     * @return Information on the group that was deleted.
     */
    @Transactional
    public GroupDataDeleteResponse deleteGroupData(GroupDataDeleteRequest groupDataDeleteRequest) {
        // TODO: validate request
        log.info("Deleting group data for group with ID {}.", groupDataDeleteRequest.groupId());
        imageRepository.deleteByOwnerId(groupDataDeleteRequest.groupId());

        return new GroupDataDeleteResponse(groupDataDeleteRequest.groupId());
    }
}
