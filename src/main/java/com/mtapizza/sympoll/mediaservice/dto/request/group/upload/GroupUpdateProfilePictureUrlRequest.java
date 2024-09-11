package com.mtapizza.sympoll.mediaservice.dto.request.group.upload;

public record GroupUpdateProfilePictureUrlRequest(
        String groupId,
        String profilePictureUrl
) {
}
