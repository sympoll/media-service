package com.mtapizza.sympoll.mediaservice.dto.request.group.image;

public record GroupImageDeleteRequest(
        String groupId,
        String imageUrl
) {
}
