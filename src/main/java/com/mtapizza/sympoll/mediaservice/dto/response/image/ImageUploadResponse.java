package com.mtapizza.sympoll.mediaservice.dto.response.image;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ImageUploadResponse (
        String ownerId,
        String imageUrl,
        String imageName,
        String type,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime timeUploaded
) {
}
