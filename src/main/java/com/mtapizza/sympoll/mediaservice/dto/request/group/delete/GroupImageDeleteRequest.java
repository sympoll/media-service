package com.mtapizza.sympoll.mediaservice.dto.request.group.delete;

public record GroupImageDeleteRequest(
        String groupId,
        String imageUrl
) {
}
