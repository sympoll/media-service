package com.mtapizza.sympoll.mediaservice.service.image;

import com.mtapizza.sympoll.mediaservice.dto.response.ImageRetrieveResponse;
import com.mtapizza.sympoll.mediaservice.dto.response.ImageUploadResponse;
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

    public ImageUploadResponse saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setData(file.getBytes());
        imageRepository.save(image);
        return new ImageUploadResponse("Successfully uploaded image", image.getId(), image.getName());
    }

    public ImageRetrieveResponse getImage(Long id) throws RuntimeException {
        Image retrievedImg = imageRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Image not found")
                );

        return new ImageRetrieveResponse(
                "Successfully retrieved image",
                retrievedImg.getName(),
                retrievedImg.getData());

    }
}
