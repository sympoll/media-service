package com.mtapizza.sympoll.mediaservice.dto.response.image;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ImageDetailsResponse(
        String ownerId,
        Long id,
        String name,
        String type,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime timeUploaded
){
}
