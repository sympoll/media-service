package com.mtapizza.sympoll.mediaservice.dto.response.user.update;

import java.util.UUID;

public record UserUpdateProfileBannerUrlResponse(
        UUID userId,
        String profileBannerUrl
) {
}
