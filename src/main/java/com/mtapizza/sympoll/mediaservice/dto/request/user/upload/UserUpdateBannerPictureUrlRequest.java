package com.mtapizza.sympoll.mediaservice.dto.request.user.upload;

import java.util.UUID;

public record UserUpdateBannerPictureUrlRequest(
        UUID userId,
        String bannerPictureUrl
) {
}
