package com.mtapizza.sympoll.mediaservice.service.image;

import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageRetrieveResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.data.format.ImageDataFormatException;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.model.image.Image;
import com.mtapizza.sympoll.mediaservice.repository.image.ImageRepository;
import com.mtapizza.sympoll.mediaservice.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageUploadResponse saveImage(MultipartFile file) throws ImageIOException {
        try {
            Image imageToSave = Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtils.compressImage(file.getBytes()))
                    .build();

            imageRepository.save(imageToSave);
            return new ImageUploadResponse("Successfully uploaded image", imageToSave.getId(), imageToSave.getName());
        } catch (IOException ex) {
            throw new ImageIOException("Failed to upload image: " + ex);
        }
    }

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
}
