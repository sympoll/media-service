package com.mtapizza.sympoll.mediaservice.dto.request.user.image;

public record UserImageDeleteRequest(
        String userId,
        String imageUrl
) {
}
