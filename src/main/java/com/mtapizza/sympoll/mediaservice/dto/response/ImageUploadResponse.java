package com.mtapizza.sympoll.mediaservice.dto.response;

public record ImageUploadResponse (
        String message,
        Long imageId,
        String imageName
) {
}
