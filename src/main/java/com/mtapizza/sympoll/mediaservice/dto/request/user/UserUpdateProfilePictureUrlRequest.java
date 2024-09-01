package com.mtapizza.sympoll.mediaservice.dto.request.user;

import java.util.UUID;

public record UserUpdateProfilePictureUrlRequest(
        UUID userId,
        String profilePictureUrl
) {
}
