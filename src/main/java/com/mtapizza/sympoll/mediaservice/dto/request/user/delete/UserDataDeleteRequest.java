package com.mtapizza.sympoll.mediaservice.dto.request.user.delete;

import java.util.UUID;

public record UserDataDeleteRequest(
        UUID userId
) {
}
