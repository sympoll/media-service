package com.mtapizza.sympoll.mediaservice.dto.request.group;

public record GroupUpdateProfilePictureUrlRequest(
        String groupId,
        String profilePictureUrl
) {
}
