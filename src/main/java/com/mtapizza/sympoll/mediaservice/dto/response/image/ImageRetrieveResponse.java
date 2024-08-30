package com.mtapizza.sympoll.mediaservice.dto.response.image;

public record ImageRetrieveResponse(
        String message,
        String name,
        byte[] image
){
}
