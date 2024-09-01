package com.mtapizza.sympoll.mediaservice.dto.request.user;

import java.util.UUID;

public record UserUpdateBannerPictureUrlRequest(
        UUID userId,
        String bannerPictureUrl
) {
}
