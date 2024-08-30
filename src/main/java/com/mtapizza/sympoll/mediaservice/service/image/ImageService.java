package com.mtapizza.sympoll.mediaservice.service.image;

import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageRetrieveResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.image.ImageUploadResponse;
import com.mtapizza.sympoll.mediaservice.exception.image.io.exception.ImageIOException;
import com.mtapizza.sympoll.mediaservice.exception.image.not.found.ImageNotFoundException;
import com.mtapizza.sympoll.mediaservice.model.image.Image;
import com.mtapizza.sympoll.mediaservice.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageUploadResponse saveImage(MultipartFile file) throws ImageIOException {
        try {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setData(file.getBytes());

            imageRepository.save(image);
            return new ImageUploadResponse("Successfully uploaded image", image.getId(), image.getName());
        } catch (IOException ex) {
            throw new ImageIOException("Failed to upload image: " + ex);
        }
    }

    public ImageRetrieveResponse getImage(Long id) throws ImageNotFoundException {
        Image retrievedImg = imageRepository
                .findById(id)
                .orElseThrow(
                        () -> new ImageNotFoundException(id)
                );

        return new ImageRetrieveResponse(
                "Successfully retrieved image",
                retrievedImg.getName(),
                retrievedImg.getData());

    }
}
