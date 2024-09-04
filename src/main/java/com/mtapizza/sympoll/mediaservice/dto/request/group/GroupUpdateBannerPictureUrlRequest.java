package com.mtapizza.sympoll.mediaservice.dto.request.group;

public record GroupUpdateBannerPictureUrlRequest(
        String groupId,
        String bannerPictureUrl
) {
}
