package com.mtapizza.sympoll.mediaservice.dto.response.user.update;

import java.util.UUID;

public record UserUpdateProfilePictureUrlResponse(
        UUID userId,
        String profilePictureUrl
) {
}
