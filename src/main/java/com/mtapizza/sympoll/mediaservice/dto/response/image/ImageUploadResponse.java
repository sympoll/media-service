package com.mtapizza.sympoll.mediaservice.dto.response.image;

public record ImageUploadResponse (
        String message,
        String imageUrl,
        String imageName,
        String type
) {
}
