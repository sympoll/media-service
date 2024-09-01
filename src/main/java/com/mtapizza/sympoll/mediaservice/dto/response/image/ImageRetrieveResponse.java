package com.mtapizza.sympoll.mediaservice.dto.response.image;

public record ImageRetrieveResponse(
        String message,
        String name,
        String type,
        byte[] image
){
}
