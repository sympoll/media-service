package com.mtapizza.sympoll.mediaservice.dto.response.image;

public record ImageUploadResponse (
        String message,
        Long imageId,
        String imageName
) {
}
