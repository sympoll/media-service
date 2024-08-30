package com.mtapizza.sympoll.mediaservice.dto.response;

public record ImageRetrieveResponse(
        String message,
        String name,
        byte[] image
){
}
