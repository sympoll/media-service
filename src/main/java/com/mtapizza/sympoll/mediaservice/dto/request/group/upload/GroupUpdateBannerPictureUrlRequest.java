package com.mtapizza.sympoll.mediaservice.dto.request.group.upload;

public record GroupUpdateBannerPictureUrlRequest(
        String groupId,
        String profileBannerUrl
) {
}
